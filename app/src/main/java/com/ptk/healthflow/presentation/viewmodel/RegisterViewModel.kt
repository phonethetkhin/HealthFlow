package com.ptk.healthflow.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.healthflow.data.local.HealthFlowDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val healthFlowDataStore: HealthFlowDataStore
) : ViewModel() {


    fun saveUserInfo(firstName: String, lastName: String, onContinue: () -> Unit) {
        viewModelScope.launch {
            healthFlowDataStore.saveFirstName(firstName)
            healthFlowDataStore.saveLastName(lastName)
            healthFlowDataStore.saveIsFirstLaunch(false)
            onContinue()
        }
    }
}
