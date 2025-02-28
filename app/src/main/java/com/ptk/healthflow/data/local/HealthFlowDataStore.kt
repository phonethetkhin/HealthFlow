package com.ptk.healthflow.data.local

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
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
        val TOTAL_NOTIFICATION_COUNT = intPreferencesKey("totalNotificationCount")
        val ACCESS_TOKEN = stringPreferencesKey("accessToken")


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
        Log.e("testASDF", "Sequence 2")

        application.dataStore.edit { preferences ->
            Log.e("testASDF", "Sequence 3")

            preferences[IS_TOKEN_EXPIRE] = isTokenExpire
        }
        Log.e("testASDF", "Sequence 4")

    }

    val totalNotificationCount: Flow<Int> = application.dataStore.data.map { preferences ->
        preferences[TOTAL_NOTIFICATION_COUNT] ?: 0
    }

    suspend fun saveTotalNotificationCount(totalNotificationCount: Int) {
        application.dataStore.edit { preferences ->
            preferences[TOTAL_NOTIFICATION_COUNT] = totalNotificationCount
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
        Log.e("testASDF", "Sequence 6")

        application.dataStore.edit { preferences ->
            Log.e("testASDF", "Sequence 7")

            preferences[ACCESS_TOKEN] = accessToken
        }
        Log.e("testASDF", "Sequence 8")

    }

}