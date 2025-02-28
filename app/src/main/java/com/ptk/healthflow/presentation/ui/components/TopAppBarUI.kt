@file:OptIn(ExperimentalMaterial3Api::class)

package com.ptk.healthflow.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ptk.healthflow.R
import com.ptk.healthflow.util.GlobalEvent
import com.ptk.healthflow.util.GlobalEventBus

@Composable
fun TopAppBarUI(notificationCount: Int) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(onClick = { GlobalEventBus.triggerEvent(GlobalEvent.MenuClicked) }) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            Box {
                // Notification Icon
                IconButton(onClick = {
                    // Handle notification click
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications"
                    )
                }

                // Display notification count if greater than 0
                if (notificationCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(
                                color = Color.Red,
                                shape = CircleShape
                            )
                            .align(Alignment.TopEnd)
                            .padding(2.dp)
                    ) {
                        Text(
                            text = notificationCount.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    )
}
