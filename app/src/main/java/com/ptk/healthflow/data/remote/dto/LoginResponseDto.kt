package com.ptk.healthflow.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val status: Int,
    val body: LoginResponseBodyDto
)

@Serializable
data class LoginResponseBodyDto(
    @SerialName("access_token")
    val accessToken: String? = null,

    @SerialName("refresh_token")
    val refreshToken: String? = null,

    @SerialName("token_type")
    val tokenType: String? = null,

    @SerialName("expires_in")
    val expiresIn: Int? = null,

    @SerialName("userid")
    val userId: String? = null
)

