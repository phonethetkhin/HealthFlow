package com.ptk.healthflow.domain.repository

import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.model.HeartRate
import com.ptk.healthflow.domain.model.Temperature

interface HealthRepository {
    suspend fun getMeasure(): Result<MeasureDto>
}
