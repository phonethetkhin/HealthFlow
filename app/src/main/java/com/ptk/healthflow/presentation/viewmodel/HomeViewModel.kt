package com.ptk.healthflow.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.model.BloodPressureType
import com.ptk.healthflow.domain.model.FeverType
import com.ptk.healthflow.domain.model.HeartBeatType
import com.ptk.healthflow.domain.model.OxygenLevelType
import com.ptk.healthflow.domain.uistates.HomeUIStates
import com.ptk.healthflow.domain.usecase.GetMeasureUseCase
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus
import com.ptk.healthflow.util.NotificationUtils
import com.ptk.healthflow.util.TokenExpiredException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    val application: Application,
    val dataStore: HealthFlowDataStore,
    private val getMeasureUseCase: GetMeasureUseCase,
) : ViewModel() {

    val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    init {
        Log.e("testASDF", "ome ViewModel init")
        setFirstName()
        fetchNoti()
        fetchData()
    }

    fun toggleIsShowDialog(isShowDialog: Boolean) {
        _uiStates.update { it.copy(isShowDialog = isShowDialog) }
    }

    private fun fetchNoti() {
        Log.e("testASDF", "ome ViewModel  Fetch Noti")

        viewModelScope.launch {
            Log.e("testASDF", "Home ViewModel ${dataStore.totalNotificationCount.first()}")

            _uiStates.update { it.copy(totalNotificationCount = dataStore.totalNotificationCount.first()) }
        }
    }

    private fun setFirstName() {
        viewModelScope.launch {
            val firstName = dataStore.firstName.first()
            val lastName = dataStore.lastName.first()
            firstName?.let { fName ->
                _uiStates.update { it.copy(firstName = fName) }
            }
            lastName?.let { lName ->
                _uiStates.update { it.copy(lastName = lName) }
            }

        }
    }

    private fun fetchData() {
        Log.e("testASDF", "ome ViewModel  ßFetchData")

        viewModelScope.launch {
            val accessToken = dataStore.accessToken.first()
            Log.e("testASDF", "accessToken $accessToken")

            accessToken?.let {
                val measureData = getMeasureUseCase(accessToken)
                measureData.fold(
                    onSuccess = { data ->
                        Log.e("testASDF", "SEquence 1")
                        val bloodPressureDiastolic = getMeasurementByType(data, 9)[0]
                        Log.e("testASDF", "SEquence 2")

                        val bloodPressureSystolic = getMeasurementByType(data, 10)[0]
                        Log.e("testASDF", "SEquence 3")

                        val heartRate = getMeasurementByType(data, 11)[0]
                        Log.e("testASDF", "SEquence 4")

                        // Since manually added data cannot retrieved from withings.
                        // Generate random temperature between 32°C and 42°C
                        Log.e("testASDF", "SEquence 5")

                        val randomTemperature = "%.1f".format(Random.nextDouble(32.0, 42.0))
                        Log.e("testASDF", "SEquence 6")

                        // Generate random oxygen saturation between 85% and 100%
                        val randomOxygen = Random.nextInt(85, 101)
                        Log.e("testASDF", "SEquence 7")

                        val heartCondition = calculateHeartRateZone(heartRate ?: 0)
                        Log.e("testASDF", "SEquence 8")

                        val feverType = checkIfFever(randomTemperature.toDouble())
                        Log.e("testASDF", "SEquence 9")

                        val bloodPressureType =
                            checkBloodPressure(
                                bloodPressureSystolic ?: 0,
                                bloodPressureDiastolic ?: 0
                            )
                        Log.e("testASDF", "SEquence 10")

                        val oxygenLevel = checkOxygenLevel(randomOxygen)
                        Log.e("testASDF", "SEquence 11")

                        toggleIsShowDialog(true)
                        _uiStates.update {
                            it.copy(
                                updateTime = (data.body?.updatetime ?: "").toString(),
                                lowBloodPressure = bloodPressureDiastolic ?: 0,
                                highBloodPressure = bloodPressureSystolic ?: 0,
                                heartRate = heartRate ?: 0,
                                temperature = randomTemperature,
                                oxygen = randomOxygen,
                                healthCondition = heartCondition,
                                feverType = feverType,
                                bloodPressureType = bloodPressureType,
                                oxygenLevelType = oxygenLevel,
                                totalNotificationCount = dataStore.totalNotificationCount.first(),
                                dialogTitle = "Something Went Wrong",
                                dialogMessage = "Successfully fetched measurements"
                            )
                        }
                        Log.e("testASDF", "SEquence 12")

                    },
                    onFailure = { error ->
                        if (error is TokenExpiredException) {
                            dataStore.saveIsTokenExpire(true)
                            GlobalEventBus.triggerEvent(GlobalEvent.TokenExpired)
                        } else {
                            _uiStates.update {
                                it.copy(
                                    dialogTitle = "Something Went Wrong",
                                    dialogMessage = error.message.toString()
                                )
                            }
                            toggleIsShowDialog(true)
                        }
                    }
                )
            }
        }
    }

    private fun getMeasurementByType(measureDto: MeasureDto, type: Int): List<Int?> {
        return measureDto.body?.measuregrps?.flatMap { group ->
            group?.measures?.filter { it?.type == type }!!.map { it?.value }
        } ?: emptyList() // If body is null, return an empty list
    }

    private fun calculateHeartRateZone(heartRate: Int): HeartBeatType {
        return when {
            heartRate < 60 -> HeartBeatType.RESTING
            heartRate in 60..100 -> HeartBeatType.MODERATE
            else -> {
                viewModelScope.launch {
                    incrementNotificationCount()
                    NotificationUtils.showHealthNotification(
                        application, "High Heart Rate Alert",
                        "Your heart rate is above normal. Current heart rate: $heartRate bpm"
                    )
                }

                HeartBeatType.HIGH
            }
        }
    }

    private fun checkIfFever(temperature: Double): FeverType {
        return when {
            temperature >= 38.0 -> {
                viewModelScope.launch {
                    incrementNotificationCount()
                    NotificationUtils.showHealthNotification(
                        application, "Fever Alert",
                        "Your temperature is high. Current temperature: $temperature °C"
                    )
                }
                FeverType.HIGH_FEVER
            }

            temperature >= 37.6 -> FeverType.MILD_FEVER
            else -> FeverType.NORMAL
        }
    }

    private fun checkBloodPressure(systolic: Int, diastolic: Int): BloodPressureType {
        return when {
            systolic < 120 && diastolic < 80 -> BloodPressureType.NORMAL
            systolic in 121..129 && diastolic < 80 -> BloodPressureType.MILD
            systolic in 130..139 || diastolic in 80..89 -> BloodPressureType.HIGH
            systolic >= 140 || diastolic >= 90 -> {
                viewModelScope.launch {
                    incrementNotificationCount()
                    NotificationUtils.showHealthNotification(
                        application, "High Blood Pressure Alert",
                        "Your blood pressure is high. Current: $systolic / $diastolic mmHg"
                    )
                }
                BloodPressureType.VERY_HIGH
            }

            else -> BloodPressureType.NORMAL
        }
    }

    private fun checkOxygenLevel(oxygenPercentage: Int): OxygenLevelType {
        return when {
            oxygenPercentage >= 95 -> OxygenLevelType.NORMAL
            oxygenPercentage in 90..94 -> {
                viewModelScope.launch {
                    incrementNotificationCount()
                    NotificationUtils.showHealthNotification(
                        application, "Low Oxygen Alert",
                        "Your oxygen level is low. Current oxygen: $oxygenPercentage%"
                    )
                }
                OxygenLevelType.LOW
            }

            else -> OxygenLevelType.NORMAL
        }
    }

    private suspend fun incrementNotificationCount() {
        val currentValue = dataStore.totalNotificationCount.first()
        dataStore.saveTotalNotificationCount(currentValue + 1)
        _uiStates.update { it.copy(totalNotificationCount = currentValue + 1) }
    }

}
