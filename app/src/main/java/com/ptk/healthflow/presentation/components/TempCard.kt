package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ptk.healthflow.R

@Composable
fun TempCard(value: String, hasFever: Boolean = false, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiary),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1.5F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (hasFever) {
                    Button(
                        modifier = Modifier,
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.error),
                        onClick = {}) {
                        Text(
                            text = "Fever",
                            color = MaterialTheme.colorScheme.onError,
                            fontWeight = MaterialTheme.typography.titleSmall.fontWeight,
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Text(
                    "Temperature",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.8f)
                )
                Text(
                    value,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary
                )

            }
            Image(
                modifier = Modifier
                    .weight(1F),
                painter = painterResource(R.drawable.temp),
                contentDescription = "HeartRate Image"
            )
        }
    }
}