package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ptk.healthflow.R

@Composable
fun BloodPressureCard(
    value: String,
    value2: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error),
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
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "Temperature",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.onError.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    value,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError
                )
                HorizontalDivider(
                    modifier = Modifier
                        .height(2.dp)
                        .width(50.dp),
                    color = MaterialTheme.colorScheme.onError
                )
                Text(
                    value2,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "mmhg",
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    color = MaterialTheme.colorScheme.onError.copy(alpha = 0.8f)
                )
            }
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                painter = painterResource(R.drawable.pressure),
                contentDescription = "HeartRate Image"
            )
        }

    }
}