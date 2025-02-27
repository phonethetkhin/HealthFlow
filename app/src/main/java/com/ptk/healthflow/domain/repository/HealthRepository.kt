package com.ptk.healthflow.domain.repository

import com.ptk.healthflow.data.remote.dto.MeasureDto

interface HealthRepository {
    suspend fun getMeasure(): Result<MeasureDto>
}
