package com.ptk.healthflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.domain.model.HeartRate
import com.ptk.healthflow.domain.model.Temperature
import com.ptk.healthflow.domain.usecase.GetHeartRateUseCase
import com.ptk.healthflow.domain.usecase.GetTemperatureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    private val getHeartRateUseCase: GetHeartRateUseCase,
    private val getTemperatureUseCase: GetTemperatureUseCase
) : ViewModel() {

    private val _heartRate = MutableStateFlow<HeartRate?>(null)
    val heartRate = _heartRate.asStateFlow()

    private val _temperature = MutableStateFlow<Temperature?>(null)
    val temperature = _temperature.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _heartRate.value = getHeartRateUseCase()
            _temperature.value = getTemperatureUseCase()
        }
    }
}
