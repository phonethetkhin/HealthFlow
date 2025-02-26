package com.ptk.healthflow.data.remote


import com.ptk.healthflow.data.remote.dto.MeasureDto
import com.ptk.healthflow.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ApiServiceImpl @Inject constructor(
    private val client: HttpClient,
) : ApiService {


    override suspend fun fetchMeasure(): MeasureDto = client.post {
        url(Constants.BASEURL + APIRoutes.GET_MEASURE)
        header("Authorization", "Bearer asdfasdfasdf")
        contentType(ContentType.Application.Json)
    }.body()


}