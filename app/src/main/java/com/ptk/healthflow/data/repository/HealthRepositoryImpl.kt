package com.ptk.healthflow.data.repository

import com.ptk.healthflow.data.remote.ApiService
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.repository.HealthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HealthRepository {

    override suspend fun getMeasure(): Result<MeasureDto> {
        return try {
            val response = apiService.fetchMeasure()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
