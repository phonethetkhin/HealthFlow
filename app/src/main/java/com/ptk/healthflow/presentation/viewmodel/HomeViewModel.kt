package com.ptk.healthflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.domain.model.HomeUIStates
import com.ptk.healthflow.domain.usecase.GetMeasureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val dataStore: HealthFlowDataStore,
    private val getMeasureUseCase: GetMeasureUseCase,
) : ViewModel() {

    val _uiStates = MutableStateFlow(HomeUIStates())
    val uiStates = _uiStates.asStateFlow()

    init {
        setFirstName()
        fetchData()
    }

    fun setFirstName() {
        viewModelScope.launch {
            val firstName = dataStore.firstName.first()
            firstName?.let { fName ->
                _uiStates.update { it.copy(firstName = fName) }
            }

        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            val accessToken = dataStore.accessToken.first()
            accessToken?.let {
                getMeasureUseCase(accessToken)
            }
        }
    }


}
