package com.ptk.healthflow.data.local

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HealthFlowDataStore @Inject constructor(private val application: Application) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("healthFlowDataStore")
        val IS_FIRST_LAUNCH = booleanPreferencesKey("isFirstLaunch")
        val IS_TOKEN_EXPIRE = booleanPreferencesKey("isTokenExpire")
        val FIRST_NAME = stringPreferencesKey("firstName")
        val LAST_NAME = stringPreferencesKey("lastName")
        val AUTH_CODE = stringPreferencesKey("authCode")
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")
        val REFRESH_TOKEN = stringPreferencesKey("refreshToken")


    }

    val isFirstLaunch: Flow<Boolean> = application.dataStore.data.map { preferences ->
        preferences[IS_FIRST_LAUNCH] ?: true
    }

    suspend fun saveIsFirstLaunch(firstLaunch: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = firstLaunch
        }
    }

    val isTokenExpire: Flow<Boolean> = application.dataStore.data.map { preferences ->
        preferences[IS_TOKEN_EXPIRE] ?: false
    }

    suspend fun saveIsTokenExpire(isTokenExpire: Boolean) {
        application.dataStore.edit { preferences ->
            preferences[IS_TOKEN_EXPIRE] = isTokenExpire
        }
    }

    val firstName: Flow<String?> = application.dataStore.data.map { preferences ->
        preferences[FIRST_NAME] ?: ""
    }

    suspend fun saveFirstName(firstName: String) {
        application.dataStore.edit { preferences ->
            preferences[FIRST_NAME] = firstName
        }
    }

    val lastName: Flow<String?> = application.dataStore.data.map { preferences ->
        preferences[LAST_NAME] ?: ""
    }

    suspend fun saveLastName(lastName: String) {
        application.dataStore.edit { preferences ->
            preferences[LAST_NAME] = lastName
        }
    }

    val authCode: Flow<String?> = application.dataStore.data.map { preferences ->
        preferences[AUTH_CODE]
    }

    suspend fun saveAuthCode(authCode: String) {
        application.dataStore.edit { preferences ->
            preferences[AUTH_CODE] = authCode
        }
    }

    val accessToken: Flow<String?> = application.dataStore.data.map { preferences ->
        preferences[ACCESS_TOKEN]
    }

    suspend fun saveAccessToken(accessToken: String) {
        application.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = accessToken
        }
    }

    val refreshToken: Flow<String?> = application.dataStore.data.map { preferences ->
        preferences[REFRESH_TOKEN]
    }

    suspend fun saveRefreshToken(refreshToken: String) {
        application.dataStore.edit { preferences ->
            preferences[REFRESH_TOKEN] = refreshToken
        }
    }
}