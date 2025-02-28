package com.ptk.healthflow.data.remote


import com.ptk.healthflow.data.remote.dto.LoginResponseDto
import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) : ApiService {
    override suspend fun login(
        isRefreshToken: Boolean,
        refreshToken: String?,
        authCode: String
    ): LoginResponseDto = client.post {
        url("${Constants.BASEURL}${APIRoutes.LOGIN}")
        parameter("action", "requesttoken")
        parameter("client_id", Constants.CLIENT_ID)
        parameter("client_secret", Constants.SECRET_KEY)
        if (isRefreshToken && refreshToken != null) {
            parameter("grant_type", "refresh_token")
            parameter("refresh_token", refreshToken)
        } else {
            parameter("grant_type", "authorization_code")
            parameter("code", authCode)
            parameter("redirect_uri", "myapp://callback")
        }

    }.body()


    override suspend fun fetchMeasure(accessToken: String): MeasureDto = client.post {
        url("https://wbsapi.withings.net/measure")
        header(HttpHeaders.Authorization, "Bearer $accessToken")
        parameter("action", "getmeas")
        parameter("meastypes", "9,10,11,12,54")
        parameter("category", 1)

    }.body()


}