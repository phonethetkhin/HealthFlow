package com.ptk.healthflow.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ptk.healthflow.presentation.ui.screen.HomeScreen
import com.ptk.healthflow.presentation.ui.screen.LoginScreen
import com.ptk.healthflow.presentation.ui.screen.OnboardingScreen
import com.ptk.healthflow.presentation.ui.screen.RegisterScreen
import kotlinx.coroutines.flow.first

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
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
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.RegisterScreen.route) { inclusive = true }
                }
            }
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen()
        }
    }
}
