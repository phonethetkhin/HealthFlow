@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ptk.healthflow.R

@Composable
fun HealthDashboard() {
    Scaffold(
        topBar = { TopAppBarUI() },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InfoCard(
                title = "Why People live longer?",
                description = "Do you know why people live longer than usual? According to..",
                backgroundColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeartRateCard(
                    "Heart Rate",
                    "137 BPM",
                    R.drawable.heart,
                    MaterialTheme.colorScheme.secondary,
                    Modifier.weight(1f)
                )
                HeartRateCard(
                    "Temperature",
                    "30 °C",
                    R.drawable.temp,
                    Color.Red,
                    Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeartRateCard(
                    "Blood Pressure",
                    "120\n90 mmhg",
                    R.drawable.blood_pressure,
                    Color.Red,
                    Modifier.weight(1f)
                )
                HeartRateCard(
                    "Oxygen", "99 %", R.drawable.oxygen, MaterialTheme.colorScheme.primary,
                    Modifier.weight(1f),
                )
            }
        }
    }
}