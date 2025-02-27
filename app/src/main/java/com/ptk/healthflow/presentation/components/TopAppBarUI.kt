@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.healthflow.presentation.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.ptk.healthflow.R

@Composable
fun TopAppBarUI(openLoginWithings: (Context) -> Unit) {
    val context = LocalContext.current
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = {
                openLoginWithings(context)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notifications"
                )
            }
        }
    )
}