package com.ptk.healthflow.data.remote

import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.data.remote.dto.MeasureDto


interface ApiService {

    suspend fun login(
        authCode: String
    ): LoginResponseDto

    suspend fun fetchMeasure(accessToken: String): MeasureDto
}