package com.ptk.healthflow.presentation.ui.navigation

sealed class Screen(val route: String) {
    data object OnboardingScreen : Screen("onboarding_screen")
    data object RegisterScreen : Screen("register_screen")
    data object LoginScreen : Screen("login_screen")
    data object HomeScreen : Screen("home_screen")
}
