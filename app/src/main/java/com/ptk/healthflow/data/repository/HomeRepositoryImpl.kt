package com.ptk.healthflow.data.repository

import com.ptk.healthflow.data.remote.ApiService
import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.domain.repository.HomeRepository
import com.ptk.healthflow.util.ClientErrorException
import com.ptk.healthflow.util.InternalServerExcetion
import com.ptk.healthflow.util.MissingParamsException
import com.ptk.healthflow.util.NetworkException
import com.ptk.healthflow.util.NotImplementedException
import com.ptk.healthflow.util.ServerErrorException
import com.ptk.healthflow.util.ServiceUnavailableException
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
        authCode: String
    ): Result<LoginResponseDto> {
        return try {

            val response = apiService.login(authCode)
            when (response.status) {
                0 -> {
                    Result.success(response)
                }

                2554, 2555, 2556, 400 -> {
                    Result.failure(NotImplementedException("Something wrong with the request or URL: ${response.status}"))
                }

                401, 403 -> {
                    Result.failure(TokenExpiredException("Token is expired, please log in again : ${response.status}"))
                }

                503 -> {
                    Result.failure(MissingParamsException("Invalid Params: Missing params: ${response.status}"))
                }

                500 -> {
                    Result.failure(InternalServerExcetion("There was an issue on the server side: ${response.status}"))
                }

                503 -> {
                    Result.failure(ServiceUnavailableException("The server is temporarily unavailable: ${response.status}"))
                }

                else -> {
                    Result.failure(UnknownApiException("Unknown error: ${response.status}"))
                }
            }
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

    override suspend fun getMeasure(accessToken: String): Result<MeasureDto> {
        return try {

            val response = apiService.fetchMeasure(accessToken)
            when (response.status) {
                0 -> {
                    Result.success(response)
                }

                2554, 2555, 2556, 400 -> {
                    Result.failure(NotImplementedException("Something wrong with the request or URL: ${response.status}"))
                }

                401, 403 -> {
                    Result.failure(TokenExpiredException("Token is expired, please log in again : ${response.status}"))
                }

                503 -> {
                    Result.failure(MissingParamsException("Invalid Params: Missing params: ${response.status}"))
                }

                500 -> {
                    Result.failure(InternalServerExcetion("There was an issue on the server side: ${response.status}"))
                }

                503 -> {
                    Result.failure(ServiceUnavailableException("The server is temporarily unavailable: ${response.status}"))
                }

                else -> {
                    Result.failure(UnknownApiException("Unknown error: ${response.status}"))
                }
            }
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
