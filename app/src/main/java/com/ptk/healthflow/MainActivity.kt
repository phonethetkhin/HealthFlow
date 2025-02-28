package com.ptk.healthflow

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)

// Check if permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
        setContent {
            HealthFlowTheme {
                val navController = rememberNavController()

                MainFunction(navController = navController)

            }
        }
    }

    @Composable
    fun MainFunction(navController: NavHostController, modifier: Modifier = Modifier) {
        // Track the start destination using a mutableState
        var startDestination by remember { mutableStateOf<String?>(null) }

        // Use LaunchedEffect to set the start destination
        LaunchedEffect(Unit) {
            startDestination = if (healthFlowDataStore.isFirstLaunch.first()) {
                Screen.OnboardingScreen.route
            } else {
                if (healthFlowDataStore.isTokenExpire.first() || healthFlowDataStore.accessToken.first() == null) {
                    Screen.LoginScreen.route
                } else {
                    Screen.HomeScreen.route
                }
            }
        }
        if (startDestination != null) {
            AppNavigation(navController = navController, startDestination!!)
        } else {
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
                loginViewModel.login(authCode)
                Log.e("DeepLink", "Authorization Code  Found")

            } else {
                Log.e("DeepLink", "Authorization Code Not Found")
            }
        }
    }


    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(
                    this,
                    "Notification permission is required to show alerts",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
