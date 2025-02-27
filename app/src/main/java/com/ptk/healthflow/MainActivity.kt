package com.ptk.healthflow

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ptk.healthflow.presentation.screen.HomeScreen
import com.ptk.healthflow.ui.theme.HealthFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink(intent)
        setContent {
            HealthFlowTheme {
                HomeScreen()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleDeepLink(intent)
    }

    private fun handleDeepLink(intent: Intent?) {
        intent?.data?.let { uri ->
            val authCode = uri.getQueryParameter("code") // Extract authorization code
            if (authCode != null) {
                // TODO: Exchange this code for an access token via Withings API
                Log.d("DeepLink", "Authorization Code: $authCode")
            } else {
                Log.e("DeepLink", "Authorization Code Not Found")
            }
        }
    }
}
