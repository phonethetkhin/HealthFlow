@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
fun HealthDashboard(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBarUI() },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
                    .padding(16.dp)
            ) {
                InfoCard(
                    title = "Why People live longer?",
                    description = "Do you know why people live longer than usual? According to..",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeartRateCard(
                    title = "Heart Rate",
                    value = "137 BPM",
                    image = R.drawable.heart,
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.fillMaxHeight().weight(1f),
                    isHeartRate = true
                )
                TempCard(
                    value ="30 °C",
                    hasFever = true,
                    modifier = Modifier.fillMaxHeight().weight(1f),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BloodPressureCard(
                    value = "120",
                    value2 = "90",
                    modifier = Modifier.fillMaxHeight().weight(1f),
                )
                HeartRateCard(
                    "Oxygen", "99 %", R.drawable.oxygen, MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxHeight().weight(1f),
                )
            }
        }
    }
}