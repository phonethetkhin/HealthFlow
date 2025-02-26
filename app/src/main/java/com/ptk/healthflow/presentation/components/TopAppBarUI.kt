@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.healthflow.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.ptk.healthflow.R

@Composable
fun TopAppBarUI() {
    TopAppBar(
        title = {
            Text(
                "Hey, Thomas",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = "Notifications"
                )
            }
        }
    )
}