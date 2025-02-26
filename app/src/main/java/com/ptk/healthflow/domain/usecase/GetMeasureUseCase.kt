package com.ptk.healthflow.domain.usecase

import com.ptk.healthflow.domain.repository.HealthRepository
import javax.inject.Inject

class GetMeasureUseCase @Inject constructor(
    private val repository: HealthRepository
) {
    suspend operator fun invoke() {
        val data = repository.getMeasure()
//        val zone = when {
//            data.bpm < 60 -> "Resting"
//            data.bpm in 60..100 -> "Moderate"
//            else -> "High"
//        }
    }
}
