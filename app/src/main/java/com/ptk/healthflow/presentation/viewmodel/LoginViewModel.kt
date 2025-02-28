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


    val _uiStates = MutableStateFlow(LoginUIStates())
    val uiStates = _uiStates.asStateFlow()

    fun updateLoadingState(isLoading: Boolean) {
        Log.e("testASDF", "Update $isLoading")

        _uiStates.update { it.copy(isLoading = isLoading) }
    }

    fun toggleIsShowErrorDialog(isShowErrorDialog: Boolean) {
        _uiStates.update {
            it.copy(
                isShowErrorDialog = isShowErrorDialog,
                errDialogTitle = "Something Went Wrong"
            )
        }
    }

    fun setErrorMessage(errorMessage: String) {
        _uiStates.update {
            it.copy(
                errDialogMessage = errorMessage
            )
        }
    }

    fun redirectToWithings(context: Context) {
        Log.e("testASDF", "HEllo REDIRECT")
        viewModelScope.launch {
            redirectToWithingsUseCase(context)
        }
    }

    fun login(authCode: String) {
        viewModelScope.launch {
            delay(1000L)
            GlobalEventBus.triggerEvent(GlobalEvent.Loading)
            healthFlowDataStore.saveAuthCode(authCode)
            val response = loginToWithingsUseCase(
                healthFlowDataStore.authCode.first()!!
            )
            response.fold(
                onSuccess = { data ->
                    if (data.body.accessToken != null) {
                        healthFlowDataStore.saveIsTokenExpire(false)
                        healthFlowDataStore.saveAccessToken(data.body.accessToken)
                        GlobalEventBus.triggerEvent(GlobalEvent.LoadingEnd)
                        GlobalEventBus.triggerEvent(GlobalEvent.NavigateHome)
                    } else {
                        GlobalEventBus.triggerEvent(GlobalEvent.LoadingEnd)
                        GlobalEventBus.triggerEvent(GlobalEvent.ShowErrorDialog)
                        GlobalEventBus.triggerEvent(GlobalEvent.SetErrorMessage("Access Token is null, need to login with withings."))

                    }
                },
                onFailure = { error ->
                    GlobalEventBus.triggerEvent(GlobalEvent.SetErrorMessage(error.message.toString()))
                    GlobalEventBus.triggerEvent(GlobalEvent.LoadingEnd)
                    GlobalEventBus.triggerEvent(GlobalEvent.ShowErrorDialog)

                    if (error is TokenExpiredException) {
                        healthFlowDataStore.saveIsTokenExpire(true)
                        GlobalEventBus.triggerEvent(GlobalEvent.TokenExpired)
                    }
                }
            )
        }
    }
}
