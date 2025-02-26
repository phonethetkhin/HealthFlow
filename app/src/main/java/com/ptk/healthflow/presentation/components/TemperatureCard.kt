package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptk.healthflow.domain.model.Temperature

@Composable
private fun TemperatureCard(temperature: Temperature) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Temperature: ${temperature.value}°C", fontSize = 18.sp)
            Text(text = "Status: ${temperature.status}", fontSize = 16.sp)
        }
    }
}
