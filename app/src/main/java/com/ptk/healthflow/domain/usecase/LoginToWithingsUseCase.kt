package com.ptk.healthflow.domain.usecase

import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.domain.repository.HomeRepository
import javax.inject.Inject

class LoginToWithingsUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {
    suspend operator fun invoke(
         authCode: String
    ): Result<LoginResponseDto> {
        return homeRepository.login(authCode)
    }
}
