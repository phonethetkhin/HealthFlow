package com.ptk.healthflow.util

sealed class GlobalEvent {
    data object NavigateHome : GlobalEvent()
    data object ShowToast : GlobalEvent()
    data object MenuClicked : GlobalEvent()
    data object TokenExpired : GlobalEvent()
    data object Loading : GlobalEvent()
    data object LoadingEnd : GlobalEvent()
    data class SetErrorMessage(val message: String) : GlobalEvent()
    data object ShowErrorDialog : GlobalEvent()
    data object DismissErrorDialog : GlobalEvent()
    data class ShowError(val message: String) : GlobalEvent()
}
