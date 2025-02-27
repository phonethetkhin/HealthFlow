package com.ptk.healthflow.domain.usecase

import com.ptk.healthflow.domain.repository.HomeRepository
import javax.inject.Inject

class GetMeasureUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(accessToken: String) {
        val data = repository.getMeasure(accessToken)
//        val zone = when {
//            data.bpm < 60 -> "Resting"
//            data.bpm in 60..100 -> "Moderate"
//            else -> "High"
//        }
    }
}
