package com.ptk.healthflow.di

import android.app.Application
import android.util.Log
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.data.repository.HealthRepositoryImpl
import com.ptk.healthflow.domain.repository.HealthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.BODY
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }
        // Timeout Plugin for setting various timeouts
        install(HttpTimeout) {
            requestTimeoutMillis = 120000L  // Request timeout in milliseconds
            connectTimeoutMillis = 120000L  // Connect timeout in milliseconds
            socketTimeoutMillis = 120000L  // Socket timeout in milliseconds
        }
    }

    class CustomHttpLogger() : Logger {
        override fun log(message: String) {
            Log.i("loggerTag", message) // Or whatever logging system you want here
        }
    }

    @Provides
    @Singleton
    fun provideHealthRepository(healthRepository: HealthRepositoryImpl): HealthRepository =
        healthRepository


        @Singleton
        @Provides
        fun providesDataStore(application: Application) =
            HealthFlowDataStore(application)

}
