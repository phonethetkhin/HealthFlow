package com.ptk.healthflow.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.healthflow.domain.model.HomeUIStates
import com.ptk.healthflow.presentation.ui.components.BloodPressureCard
import com.ptk.healthflow.presentation.ui.components.HeartRateCard
import com.ptk.healthflow.presentation.ui.components.OxygenCard
import com.ptk.healthflow.presentation.ui.components.TempCard
import com.ptk.healthflow.presentation.ui.components.TopAppBarUI
import com.ptk.healthflow.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiStates.collectAsState()
    HealthScreenContent(uiStates)
}

@Composable
fun HealthScreenContent(uiStates: HomeUIStates, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBarUI() },
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Hey, ${uiStates.firstName}",
                fontWeight = FontWeight.Black,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .height(2.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeartRateCard(
                    value = "${uiStates.heartRate} BPM",
                    heartBeatType = uiStates.healthCondition,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
                TempCard(
                    value = "${uiStates.temperature} °C",
                    feverType = uiStates.feverType,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BloodPressureCard(
                    value = "${uiStates.highBloodPressure}",
                    value2 = "${uiStates.lowBloodPressure}",
                    bloodPressureType = uiStates.bloodPressureType,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
                OxygenCard(
                    "${uiStates.oxygen} %",
                    oxygenLevelType = uiStates.oxygenLevelType,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
            }
        }
    }
}













