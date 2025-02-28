package com.ptk.healthflow.util

sealed class GlobalEvent {
    data object NavigateHome : GlobalEvent()
    data object ShowToast : GlobalEvent()
    data object Loading : GlobalEvent()
    data class ShowError(val message: String) : GlobalEvent()
}
