package com.ptk.healthflow.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptk.healthflow.presentation.ui.screen.HomeScreen
import com.ptk.healthflow.presentation.ui.screen.OnboardingScreen
import com.ptk.healthflow.presentation.ui.screen.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.OnboardingScreen.route
    ) {
        composable(Screen.OnboardingScreen.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.RegisterScreen.route) {
                        popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.RegisterScreen.route) { inclusive = true }
                }
            }
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}
