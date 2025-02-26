//package com.ptk.healthflow.data.repository
//
//import com.ptk.healthflow.domain.repository.HealthRepository
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class HealthRepositoryImpl @Inject constructor(
//    private val apiService: HealthApiService
//) : HealthRepository {
//
//    override suspend fun getHeartRate(): Result<Int> {
//        return try {
//            val response = apiService.fetchHeartRate()
//            Result.success(response.heartRate)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//
//    override suspend fun getTemperature(): Result<Double> {
//        return try {
//            val response = apiService.fetchTemperature()
//            Result.success(response.temperature)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }
//}
