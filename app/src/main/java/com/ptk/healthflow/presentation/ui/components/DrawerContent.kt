package com.ptk.healthflow.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.ptk.healthflow.R
import com.ptk.healthflow.presentation.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(userName: String, navController: NavController, drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DrawerHeader(
            profileImage = painterResource(id = R.drawable.profile_default_svgrepo_com), // Replace with your image
            userName = userName
        )

        Divider()

        DrawerItem(
            label = "Profile",
            icon = Icons.Default.Person,
            onClick = {
                coroutineScope.launch {
                    drawerState.close()
                }
            }
        )

        DrawerItem(
            label = "Logout",
            icon = painterResource(R.drawable.baseline_exit_to_app_24),
            onClick = {
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(Screen.HomeScreen.route) { inclusive = true }
                }
                coroutineScope.launch {
                    drawerState.close()
                }
            }
        )
    }
}
