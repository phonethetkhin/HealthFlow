package com.ptk.healthflow.presentation.screen

import HeartRateCard
import TemperatureCard
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.healthflow.presentation.viewmodel.HealthViewModel

@Composable
fun HealthScreen(viewModel: HealthViewModel = hiltViewModel()) {
    val heartRate by viewModel.heartRate.collectAsState()
    val temperature by viewModel.temperature.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        heartRate?.let { HeartRateCard(it) }
        temperature?.let { TemperatureCard(it) }
    }
}
