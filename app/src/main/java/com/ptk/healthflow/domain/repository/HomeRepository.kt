package com.ptk.healthflow.domain.repository

import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.data.remote.dto.MeasureDto

interface HomeRepository {
    suspend fun login(authCode: String): Result<LoginResponseDto>
    suspend fun getMeasure(accessToken: String): Result<MeasureDto>
}
