package com.ptk.healthflow.presentation.ui.screen

import android.view.Menu
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ptk.healthflow.domain.uistates.HomeUIStates
import com.ptk.healthflow.presentation.ui.components.BloodPressureCard
import com.ptk.healthflow.presentation.ui.components.DrawerContent
import com.ptk.healthflow.presentation.ui.components.HeartRateCard
import com.ptk.healthflow.presentation.ui.components.InfoDialog
import com.ptk.healthflow.presentation.ui.components.OxygenCard
import com.ptk.healthflow.presentation.ui.components.TempCard
import com.ptk.healthflow.presentation.ui.components.TopAppBarUI
import com.ptk.healthflow.presentation.ui.navigation.Screen
import com.ptk.healthflow.presentation.viewmodel.HomeViewModel
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus
import kotlinx.coroutines.CoroutineScope

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiStates.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        GlobalEventBus.eventFlow.collect { event ->
            when (event) {
                is GlobalEvent.NavigateHome -> {}

                is GlobalEvent.Loading -> {}

                is GlobalEvent.ShowError -> {}

                is GlobalEvent.TokenExpired -> {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.HomeScreen.route) { inclusive = true }
                    }
                }

                is GlobalEvent.MenuClicked -> {
                    drawerState.open()
                }

                else -> {}
            }
        }
    }
    if (uiStates.isShowDialog) {
        InfoDialog(
            title = "Information",
            message = "This is an informational message for the user.",
            lastSyncTime = uiStates.updateTime,
            heartRate = uiStates.heartRate,
            temperature = uiStates.temperature,
            upperBP = uiStates.highBloodPressure,
            lowerBP = uiStates.lowBloodPressure,
            oxygen = uiStates.oxygen,
            onDismiss = { viewModel.toggleIsShowDialog(false) }
        )
    }
    HealthScreenContent(drawerState, navController, uiStates)
}

@Composable
fun HealthScreenContent(
    drawerState: DrawerState,
    navController: NavHostController,
    uiStates: HomeUIStates,
    modifier: Modifier = Modifier
) {


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    "${uiStates.firstName} ${uiStates.lastName}",
                    navController,
                    drawerState
                )
            }
        }
    ) {
        Scaffold(
            topBar = { TopAppBarUI(uiStates.totalNotificationCount) },
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
}













