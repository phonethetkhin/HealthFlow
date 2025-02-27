package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ptk.healthflow.R

@Composable
fun InfoCard(
    title: String,
    description: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxHeight(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
    ) {
        Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            )
            Text(
                description,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(R.drawable.news),
                contentDescription = "Health News Image"
            )
        }
    }
}