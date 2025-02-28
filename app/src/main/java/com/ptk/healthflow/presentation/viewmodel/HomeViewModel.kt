package com.ptk.healthflow.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.model.BloodPressureType
import com.ptk.healthflow.domain.model.FeverType
import com.ptk.healthflow.domain.model.HeartBeatType
import com.ptk.healthflow.domain.model.HomeUIStates
import com.ptk.healthflow.domain.model.OxygenLevelType
import com.ptk.healthflow.domain.usecase.GetMeasureUseCase
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
    val dataStore: HealthFlowDataStore,
    private val getMeasureUseCase: GetMeasureUseCase,
) : ViewModel() {

    val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    init {
        setFirstName()
        fetchData()
    }

    fun setFirstName() {
        viewModelScope.launch {
            val firstName = dataStore.firstName.first()
            firstName?.let { fName ->
                _uiStates.update { it.copy(firstName = fName) }
            }

        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val accessToken = dataStore.accessToken.first()
            accessToken?.let {
                val measureData = getMeasureUseCase(accessToken)
                measureData.fold(
                    onSuccess = { data ->
                        val bloodPressureDiastolic = getMeasurementByType(data, 9)[0]
                        val bloodPressureSystolic = getMeasurementByType(data, 10)[0]
                        val heartRate = getMeasurementByType(data, 11)[0]

                        // Since manually added data cannot retrieved from withings.
                        // Generate random temperature between 32°C and 42°C
                        val randomTemperature = "%.1f".format(Random.nextDouble(32.0, 42.0))

                        // Generate random oxygen saturation between 85% and 100%
                        val randomOxygen = Random.nextInt(85, 101)

                        val heartCondition = calculateHeartRateZone(heartRate ?: 0)
                        val feverType = checkIfFever(randomTemperature.toDouble())
                        val bloodPressureType =
                            checkBloodPressure(
                                bloodPressureSystolic ?: 0,
                                bloodPressureDiastolic ?: 0
                            )
                        val oxygenLevel = checkOxygenLevel(randomOxygen)
                        _uiStates.update {
                            it.copy(
                                updateTime = data.body?.updatetime ?: "",
                                lowBloodPressure = bloodPressureDiastolic ?: 0,
                                highBloodPressure = bloodPressureSystolic ?: 0,
                                heartRate = heartRate ?: 0,
                                temperature = randomTemperature,
                                oxygen = randomOxygen,
                                healthCondition = heartCondition,
                                feverType = feverType,
                                bloodPressureType = bloodPressureType,
                                oxygenLevelType = oxygenLevel
                            )
                        }
                    },
                    onFailure = { error ->
                        Log.e("onError", "Error : ${error.message}")
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
            else -> HeartBeatType.HIGH
        }
    }

    private fun checkIfFever(temperature: Double): FeverType {
        return when {
            temperature >= 38.0 -> FeverType.HIGH_FEVER
            temperature >= 37.6 -> FeverType.MILD_FEVER
            else -> FeverType.NORMAL
        }
    }

    private fun checkBloodPressure(systolic: Int, diastolic: Int): BloodPressureType {
        return when {
            systolic < 120 && diastolic < 80 -> BloodPressureType.NORMAL
            systolic in 121..129 && diastolic < 80 -> BloodPressureType.MILD
            systolic in 130..139 || diastolic in 80..89 -> BloodPressureType.HIGH
            systolic >= 140 || diastolic >= 90 -> BloodPressureType.VERY_HIGH
            else -> BloodPressureType.NORMAL
        }
    }

    fun checkOxygenLevel(oxygenPercentage: Int): OxygenLevelType {
        return when {
            oxygenPercentage >= 95 -> OxygenLevelType.NORMAL
            oxygenPercentage in 90..94 -> OxygenLevelType.LOW
            else -> OxygenLevelType.NORMAL
        }
    }

}
