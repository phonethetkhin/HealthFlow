package com.ptk.healthflow.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.domain.model.HomeUIStates
import com.ptk.healthflow.domain.usecase.GetMeasureUseCase
import com.ptk.healthflow.domain.usecase.LoginToWithingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMeasureUseCase: GetMeasureUseCase,
    private val loginToWithingsUseCase: LoginToWithingsUseCase
) : ViewModel() {

    val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    private fun fetchData() {
        viewModelScope.launch {
//            _heartRate.value = getMeasureUseCase()
        }
    }

    fun loginToWithings(context: Context) {
        viewModelScope.launch {
            loginToWithingsUseCase(context)
        }
    }
}
