package com.ptk.healthflow.domain.repository

import com.ptk.healthflow.domain.model.HeartRate
import com.ptk.healthflow.domain.model.Temperature

interface HealthRepository {
    suspend fun getHeartRate(): HeartRate
    suspend fun getTemperature(): Temperature
}
