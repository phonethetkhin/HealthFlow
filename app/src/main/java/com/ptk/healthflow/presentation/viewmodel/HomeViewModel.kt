package com.ptk.healthflow.presentation.viewmodel

import android.app.Application
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        _uiStates.update { it.copy(isLoading = true) }
        setFirstName()
        fetchNoti()
        fetchData()
    }

    fun toggleIsShowDialog(isShowDialog: Boolean) {
        _uiStates.update { it.copy(isShowDialog = isShowDialog) }
    }

    fun toggleIsShowErrorDialog(isShowErrorDialog: Boolean) {
        _uiStates.update { it.copy(isShowErrorDialog = isShowErrorDialog) }
    }

    private fun fetchNoti() {
        viewModelScope.launch {
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
        viewModelScope.launch {
            val accessToken = withContext(Dispatchers.IO) { dataStore.accessToken.first() }
            accessToken?.let {
                val measureData = withContext(Dispatchers.IO) { getMeasureUseCase(accessToken) }
                measureData.fold(
                    onSuccess = { data ->
                        if (!data.body?.measuregrps.isNullOrEmpty()) {
                            val bloodPressureDiastolic = getMeasurementByType(data, 9)[0]

                            val bloodPressureSystolic = getMeasurementByType(data, 10)[0]

                            val heartRate = getMeasurementByType(data, 11)[0]

                            // Since manually added data cannot retrieved from withings.
                            // Generate random temperature between 32°C and 42°C

                            val randomTemperature = "%.1f".format(Random.nextDouble(32.0, 42.0))
                            val randomOxygen = Random.nextInt(85, 101)
                            val heartCondition =
                                async { calculateHeartRateZone(heartRate ?: 0) }.await()
                            val feverType =
                                async { checkIfFever(randomTemperature.toDouble()) }.await()
                            val bloodPressureType =
                                async {
                                    checkBloodPressure(
                                        bloodPressureSystolic ?: 0,
                                        bloodPressureDiastolic ?: 0
                                    )
                                }.await()
                            val oxygenLevel =
                                async { checkOxygenLevel(randomOxygen) }.await()


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
                                    errDialogTitle = "Something Went Wrong",
                                    errDialogMessage = "Successfully fetched measurements"
                                )
                            }
                            _uiStates.update { it.copy(isLoading = false) }
                            toggleIsShowDialog(true)
                        } else {
                            _uiStates.update {
                                it.copy(
                                    errDialogTitle = "Empty Data",
                                    errDialogMessage = "The data is empty. Please make sure to add data in the Withings application.",
                                    isLoading = false,
                                )
                            }
                            toggleIsShowErrorDialog(true)
                        }
                    },
                    onFailure = { error ->
                        if (error is TokenExpiredException) {
                            dataStore.saveIsTokenExpire(true)
                            GlobalEventBus.triggerEvent(GlobalEvent.TokenExpired)
                        } else {
                            _uiStates.update {
                                it.copy(
                                    errDialogTitle = "Something Went Wrong",
                                    errDialogMessage = error.message.toString(),
                                    isLoading = false
                                )
                            }
                            toggleIsShowErrorDialog(true)
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

    private suspend fun calculateHeartRateZone(heartRate: Int): HeartBeatType {
        val heartRateType = when {
            heartRate < 60 -> HeartBeatType.RESTING
            heartRate in 60..100 -> HeartBeatType.MODERATE
            else -> {
                showNoti(
                    "High Heart Rate Alert",
                    "Your heart rate is above normal. Current heart rate: $heartRate bpm"
                )
                HeartBeatType.HIGH
            }
        }
        return heartRateType

    }

    private suspend fun checkIfFever(temperature: Double): FeverType {
        return when {
            temperature >= 38.0 -> {
                showNoti(
                    "Fever Alert",
                    "Your temperature is high. Current temperature: $temperature °C"
                )
                FeverType.HIGH_FEVER
            }

            temperature >= 37.6 -> FeverType.MILD_FEVER
            else -> FeverType.NORMAL
        }

    }

    private suspend fun checkBloodPressure(systolic: Int, diastolic: Int): BloodPressureType {
        return when {
            systolic < 120 && diastolic < 80 -> BloodPressureType.NORMAL
            systolic in 121..129 && diastolic < 80 -> BloodPressureType.MILD
            systolic in 130..139 || diastolic in 80..89 -> BloodPressureType.HIGH
            systolic >= 140 || diastolic >= 90 -> {
                showNoti(
                    "High Blood Pressure Alert",
                    "Your blood pressure is high. Current: $systolic / $diastolic mmHg"
                )
                BloodPressureType.VERY_HIGH
            }

            else -> BloodPressureType.NORMAL
        }
    }

    private suspend fun checkOxygenLevel(oxygenPercentage: Int): OxygenLevelType {
        return when {
            oxygenPercentage >= 95 -> OxygenLevelType.NORMAL
            oxygenPercentage in 90..94 -> {
                showNoti(
                    "Low Oxygen Alert",
                    "Your oxygen level is low. Current oxygen: $oxygenPercentage%"
                )
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

    private suspend fun showNoti(title: String, message: String) {
        incrementNotificationCount()
        NotificationUtils.showHealthNotification(
            application, title,
            message
        )
    }

}
