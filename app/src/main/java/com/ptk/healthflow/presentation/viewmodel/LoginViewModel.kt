package com.ptk.healthflow.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.domain.uistates.LoginUIStates
import com.ptk.healthflow.domain.usecase.LoginToWithingsUseCase
import com.ptk.healthflow.domain.usecase.RedirectToWithingsUseCase
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus
import com.ptk.healthflow.util.TokenExpiredException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val healthFlowDataStore: HealthFlowDataStore,
    private val loginToWithingsUseCase: LoginToWithingsUseCase,
    private val redirectToWithingsUseCase: RedirectToWithingsUseCase
) : ViewModel() {

    init {
        Log.e("testASDF", "init")

    }

    val _uiStates = MutableStateFlow(LoginUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun updateLoadingState(isLoading: Boolean) {
        Log.e("testASDF", "Update $isLoading")

        _uiStates.update { it.copy(isLoading = isLoading) }
    }

    fun redirectToWithings(context: Context) {
        Log.e("testASDF", "HEllo REDIRECT")
        viewModelScope.launch {
            redirectToWithingsUseCase(context)
        }
    }

    fun login(authCode: String) {
        Log.e("onLogin", "Login : $authCode")
        viewModelScope.launch {
            delay(1000L)
            GlobalEventBus.triggerEvent(GlobalEvent.Loading)

            healthFlowDataStore.saveAuthCode(authCode)
            val response = loginToWithingsUseCase(
                healthFlowDataStore.authCode.first()!!
            )
            response.fold(
                onSuccess = { data ->
                    Log.e("testASDF", data.body.toString())
                    // Save the access token and wait for it to be saved
                    data.body.accessToken?.let {
                        Log.e("testASDF", "Sequence 1")

                        healthFlowDataStore.saveIsTokenExpire(false)
                        Log.e("testASDF", "Sequence 5")

                        healthFlowDataStore.saveAccessToken(data.body.accessToken)
                        Log.e("testASDF", "Sequence 9")

                        GlobalEventBus.triggerEvent(GlobalEvent.NavigateHome)
                        Log.e("testASDF", "Sequence 10")

                    }

                },
                onFailure = { error ->
                    if (error is TokenExpiredException) {
                        healthFlowDataStore.saveIsTokenExpire(true)
                        GlobalEventBus.triggerEvent(GlobalEvent.TokenExpired)
                    }
                }
            )
        }
    }
}
