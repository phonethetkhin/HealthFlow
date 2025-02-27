package com.ptk.healthflow.data.repository

import com.ptk.healthflow.data.remote.ApiService
import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.repository.HomeRepository
import com.ptk.healthflow.util.ClientErrorException
import com.ptk.healthflow.util.NetworkException
import com.ptk.healthflow.util.ServerErrorException
import com.ptk.healthflow.util.TokenExpiredException
import com.ptk.healthflow.util.UnknownApiException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HomeRepository {
    override suspend fun login(
        isRefreshToken: Boolean,
        refreshToken: String?, authCode: String
    ): Result<LoginResponseDto> {
        return try {
            val response = apiService.login(isRefreshToken, refreshToken, authCode)
            Result.success(response)
        } catch (e: ClientRequestException) { // 4xx Errors
            if (e.response.status.value == 401 || e.response.status.value == 403) {
                Result.failure(TokenExpiredException("Token Expire: ${e.response.status.description}"))
            } else {
                Result.failure(ClientErrorException("Invalid request: ${e.response.status.description}"))
            }
        } catch (e: ServerResponseException) { // 5xx Errors
            Result.failure(ServerErrorException("Server error: ${e.response.status.description}"))
        } catch (e: IOException) { // Network Issues
            Result.failure(NetworkException("No internet connection. Please check your network."))
        } catch (e: Exception) { // Other unexpected errors
            Result.failure(UnknownApiException("Something went wrong: ${e.message}"))
        }
    }

    override suspend fun getMeasure(accessToken: String): Result<MeasureDto> {
        return try {
            val response = apiService.fetchMeasure(accessToken)
            Result.success(response)
        } catch (e: ClientRequestException) { // 4xx Errors
            Result.failure(ClientErrorException("Invalid request: ${e.response.status.description}"))
        } catch (e: ServerResponseException) { // 5xx Errors
            Result.failure(ServerErrorException("Server error: ${e.response.status.description}"))
        } catch (e: IOException) { // Network Issues
            Result.failure(NetworkException("No internet connection. Please check your network."))
        } catch (e: Exception) { // Other unexpected errors
            Result.failure(UnknownApiException("Something went wrong: ${e.message}"))
        }

    }
}
