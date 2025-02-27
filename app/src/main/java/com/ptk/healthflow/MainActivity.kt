package com.ptk.healthflow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ptk.healthflow.data.local.HealthFlowDataStore
import com.ptk.healthflow.presentation.ui.navigation.AppNavigation
import com.ptk.healthflow.presentation.ui.navigation.Screen
import com.ptk.healthflow.presentation.ui.theme.HealthFlowTheme
import com.ptk.healthflow.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var healthFlowDataStore: HealthFlowDataStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)

        setContent {
            HealthFlowTheme {
                MainFunction()
            }
        }
    }

    @Composable
    fun MainFunction(modifier: Modifier = Modifier) {
        // Track the start destination using a mutableState
        var startDestination by remember { mutableStateOf<String?>(null) }

        // Use LaunchedEffect to set the start destination
        LaunchedEffect(Unit) {
            startDestination = if (healthFlowDataStore.isFirstLaunch.first()) {
                Screen.OnboardingScreen.route
            } else {
                Screen.LoginScreen.route
            }
        }
        if(startDestination!=null) {
            AppNavigation(startDestination!!)
        }else{
            CircularProgressIndicator()
        }


    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            val authCode = uri.getQueryParameter("code") // Extract authorization code
            val loginViewModel: LoginViewModel by viewModels()
            if (authCode != null) {
                loginViewModel.login(authCode, false, this@MainActivity)
                Log.e("DeepLink", "Authorization Code  Found")

            } else {
                Log.e("DeepLink", "Authorization Code Not Found")
            }
        }
    }
}
