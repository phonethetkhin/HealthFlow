package com.ptk.healthflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ptk.healthflow.presentation.screen.HomeScreen
import com.ptk.healthflow.ui.theme.HealthFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HealthFlowTheme {
                HomeScreen()
            }
        }
    }
}
