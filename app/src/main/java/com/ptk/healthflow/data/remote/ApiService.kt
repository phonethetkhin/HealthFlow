package com.ptk.healthflow.data.remote

import com.ptk.healthflow.data.remote.dto.MeasureDto


interface ApiService {

    suspend fun fetchMeasure(): MeasureDto
}