package com.ptk.healthflow.domain.uistates

data class LoginUIStates(
    val isLoading: Boolean = false,
    val isShowErrorDialog: Boolean = false,
    var errDialogTitle: String = "",
    var errDialogMessage: String = "",
)
