package com.ptk.healthflow.presentation.screen

import android.content.Context
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.healthflow.R
import com.ptk.healthflow.presentation.components.BloodPressureCard
import com.ptk.healthflow.presentation.components.HeartRateCard
import com.ptk.healthflow.presentation.components.TempCard
import com.ptk.healthflow.presentation.components.TopAppBarUI
import com.ptk.healthflow.presentation.components.TopSection
import com.ptk.healthflow.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiStates.collectAsState()
    HealthScreenContent(viewModel::loginToWithings)
}

@Composable
fun HealthScreenContent(loginToWithings: (Context) -> Unit, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { TopAppBarUI(loginToWithings) },
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
            TopSection()

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
                    title = "Heart Rate",
                    value = "137 BPM",
                    image = R.drawable.heart,
                    backgroundColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    isHeartRate = true
                )
                TempCard(
                    value = "30 °C",
                    hasFever = true,
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
                    value = "120",
                    value2 = "90",
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
                HeartRateCard(
                    "Oxygen", "99 %", R.drawable.oxygen, MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                )
            }
        }
    }
}













