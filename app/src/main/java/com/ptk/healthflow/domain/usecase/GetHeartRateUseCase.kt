package com.ptk.healthflow.domain.usecase

import com.ptk.healthflow.domain.model.HeartRate
import com.ptk.healthflow.domain.repository.HealthRepository
import javax.inject.Inject

class GetHeartRateUseCase @Inject constructor(
    private val repository: HealthRepository
) {
    suspend operator fun invoke(): HeartRate {
        val data = repository.getHeartRate()
        val zone = when {
            data.bpm < 60 -> "Resting"
            data.bpm in 60..100 -> "Moderate"
            else -> "High"
        }
        return data.copy(zone = zone)
    }
}
