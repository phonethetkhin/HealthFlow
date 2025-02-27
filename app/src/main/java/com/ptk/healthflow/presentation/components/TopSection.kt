package com.ptk.healthflow.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ColumnScope.TopSection(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                "Hey, Thomas",
                fontWeight = FontWeight.Black,
                fontSize = MaterialTheme.typography.displaySmall.fontSize,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = modifier.height(8.dp))
            InfoCard(
                title = "Why People live longer?",
                description = "Do you know why people live longer than usual? According to..asdfasdfasdfjlajsdfkjalsjdflkajdlsfa;dfadfjaldjalsdjfakldjsflkjsdlfalkdjflkasdjfklajsdklfjalskdjflaksdjfklj",
                backgroundColor = MaterialTheme.colorScheme.primary,
            )
        }

    }
}