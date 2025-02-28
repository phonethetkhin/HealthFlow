package com.ptk.healthflow.presentation.ui.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ptk.healthflow.R
import com.ptk.healthflow.presentation.ui.components.ErrorDialog
import com.ptk.healthflow.presentation.ui.navigation.Screen
import com.ptk.healthflow.presentation.viewmodel.LoginViewModel
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    Log.e("logINUISTATE", "LoginScreen ASdasdf!")

    val uiStates by loginViewModel.uiStates.collectAsState()
    Log.e("logINUISTATE", "UISTSSCREEN $uiStates")

    LaunchedEffect(Unit) {
        GlobalEventBus.eventFlow.collect { event ->
            when (event) {
                is GlobalEvent.NavigateHome -> {
                    Log.e("testASDF", "Sequence 11")

                    loginViewModel.updateLoadingState(false)

                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }

                is GlobalEvent.Loading -> {
                    loginViewModel.updateLoadingState(true)
                }

                is GlobalEvent.LoadingEnd -> {
                    loginViewModel.updateLoadingState(false)
                }

                is GlobalEvent.ShowErrorDialog -> {
                    loginViewModel.toggleIsShowErrorDialog(true)
                }

                is GlobalEvent.DismissErrorDialog -> {
                    loginViewModel.toggleIsShowErrorDialog(false)
                }

                is GlobalEvent.SetErrorMessage -> {
                    loginViewModel.setErrorMessage(event.message)
                }

                else -> {}
            }
        }
    }
    Log.e("testASDF", "UISTATE" + uiStates.isLoading.toString())
    if (uiStates.isLoading) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading)) // Replace with your Lottie animation file
        LottieAnimation(
            composition = composition,
            modifier = Modifier
                .padding(8.dp), // Add padding if needed
            iterations = LottieConstants.IterateForever
        )
    }
    Log.e("testASDF", "ERROR DIA" + uiStates.isShowErrorDialog.toString())

    if (uiStates.isShowErrorDialog) {
        ErrorDialog(
            title = uiStates.errDialogTitle,
            message = uiStates.errDialogMessage
        ) {
            loginViewModel.toggleIsShowErrorDialog(false)
        }
    }
    LoginScreenContent(loginViewModel)
}

@Composable
fun LoginScreenContent(loginViewModel: LoginViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Image(
            painter = painterResource(id = R.drawable.health_flow_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(300.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        val context = LocalContext.current

        Button(
            onClick = {
                loginViewModel.redirectToWithings(context)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Login To Withings")
        }
    }
}
