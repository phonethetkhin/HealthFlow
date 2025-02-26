package com.ptk.healthflow.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.healthflow.presentation.components.HealthDashboard
import com.ptk.healthflow.presentation.viewmodel.HealthViewModel

@Composable
fun HomeScreen(viewModel: HealthViewModel = hiltViewModel()) {
    val heartRate by viewModel.heartRate.collectAsState()
    val temperature by viewModel.temperature.collectAsState()

    HealthScreenContent()
}

@Composable
fun HealthScreenContent(modifier: Modifier = Modifier) {
    HealthDashboard()
}













