package com.ptk.healthflow.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ptk.healthflow.R
import com.ptk.healthflow.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(registerViewModel: RegisterViewModel = hiltViewModel(), onContinue: () -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val isButtonEnabled = firstName.isNotBlank() && lastName.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Smart Insights for Better Health",
            fontSize = MaterialTheme.typography.displaySmall.fontSize,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center,
            lineHeight = 50.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Column {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                trailingIcon = {
                    if (firstName.isNotBlank()) {
                        IconButton(
                            onClick = { firstName = "" },
                            modifier = Modifier
                                .size(25.dp)
                                .background(MaterialTheme.colorScheme.error, shape = CircleShape)
                                .padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = "Close icon",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                },
                label = { Text("Enter First Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                trailingIcon = {
                    if (lastName.isNotBlank()) {
                        IconButton(
                            onClick = { lastName = "" },
                            modifier = Modifier
                                .size(25.dp)
                                .background(
                                    MaterialTheme.colorScheme.error,
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = "Close icon",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                },
                label = { Text("Enter Last Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )
        }

        Button(
            onClick =
            { registerViewModel.saveUserInfo(firstName, lastName, onContinue) },
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(100.dp, 50.dp),
            enabled = isButtonEnabled,
            shape = RoundedCornerShape(50.dp)
        ) {
            Text(text = "Continue")
        }
    }
}
