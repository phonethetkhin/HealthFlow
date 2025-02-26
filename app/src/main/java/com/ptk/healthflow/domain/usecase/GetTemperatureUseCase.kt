package com.ptk.healthflow.domain.usecase

import com.ptk.healthflow.domain.model.Temperature
import com.ptk.healthflow.domain.repository.HealthRepository
import javax.inject.Inject

class GetTemperatureUseCase @Inject constructor(
    private val repository: HealthRepository
) {
    suspend operator fun invoke(): Temperature {
        val data = repository.getTemperature()
        val status = if (data.value > 37.5) "Fever" else "Normal"
        return data.copy(status = status)
    }
}
