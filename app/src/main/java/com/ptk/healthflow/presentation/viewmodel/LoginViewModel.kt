package com.ptk.healthflow.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.domain.usecase.LoginToWithingsUseCase
import com.ptk.healthflow.domain.usecase.RedirectToWithingsUseCase
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus
import com.ptk.healthflow.util.TokenExpiredException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val healthFlowDataStore: HealthFlowDataStore,
    private val loginToWithingsUseCase: LoginToWithingsUseCase,
    private val redirectToWithingsUseCase: RedirectToWithingsUseCase
) : ViewModel() {

    private val _loadingState = MutableStateFlow(false) // Initial state is false (not loading)

    // Expose it as a StateFlow to observe in UI.
    val loadingState: StateFlow<Boolean> = _loadingState

    fun updateLoadingState(isLoading: Boolean) {
        _loadingState.value = isLoading
    }

    fun redirectToWithings(context: Context) {
        Log.e("testASDF", "HEllo REDIRECT")
        viewModelScope.launch {
            redirectToWithingsUseCase(context)
        }
    }

    fun login(authCode: String, isRefreshToken: Boolean, context: Context) {
        Log.e("onLogin", "Login : $authCode")
        GlobalEventBus.triggerEvent(GlobalEvent.Loading)
        viewModelScope.launch {
            healthFlowDataStore.saveAuthCode(authCode)
            val response = loginToWithingsUseCase(
                isRefreshToken,
                healthFlowDataStore.refreshToken.first(),
                healthFlowDataStore.authCode.first()!!
            )
            response.fold(
                onSuccess = { data ->
                    data.body.accessToken?.let {
                        healthFlowDataStore.saveAccessToken(data.body.accessToken)
                    }
                    data.body.refreshToken?.let {
                        healthFlowDataStore.saveRefreshToken(data.body.refreshToken)
                    }
                    Log.e("onSuccess", "Data : ${data.body.accessToken}")
                    GlobalEventBus.triggerEvent(GlobalEvent.NavigateHome)
                },
                onFailure = { error ->
                    Log.e("onError", "Error : ${error.message}")

                    if (error is TokenExpiredException) {
                        healthFlowDataStore.saveIsTokenExpire(true)
                        login(authCode, true, context)
                    }
                }
            )
        }
    }
}
