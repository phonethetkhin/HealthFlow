package com.ptk.healthflow.domain.usecase

import android.util.Log
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.repository.HomeRepository
import javax.inject.Inject

class GetMeasureUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(accessToken: String): Result<MeasureDto> {
        Log.e("testASDF","Fetch Data UseCase")

        return repository.getMeasure(accessToken)

    }
}
