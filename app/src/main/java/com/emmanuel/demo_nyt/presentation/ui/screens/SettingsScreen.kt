package com.emmanuel.demo_nyt.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor
import com.emmanuel.demo_nyt.presentation.ui.components.ErrorDialog
import com.emmanuel.demo_nyt.presentation.viewmodel.SettingViewModel
import com.emmanuel.demo_nyt.presentation.viewmodel.SettingViewModelFactory

/**
 * Settings Screen to create ir update the api key.
 * @return a composable that displays a TextField to create/change the api key.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingViewModel = viewModel(
        factory = SettingViewModelFactory()
    )
) {
    //Variable for handle user input
    var apiKeyValue by remember { mutableStateOf("") }

    val onSaveResult by settingsViewModel.onSaveResult.observeAsState()

    // Display the alert dialog if the request result is an error
    onSaveResult?.let { result ->
        when (result) {
            is SettingViewModel.OnSaveResult.Error -> {
                apiKeyValue = ""
                ErrorDialog(
                    onDialogDismiss = { settingsViewModel.resetResponse() },
                    message = result.message
                )
            }

            is SettingViewModel.OnSaveResult.Success -> {
                ErrorDialog(
                    onDialogDismiss = { settingsViewModel.resetResponse() },
                    title = "Result",
                    message = "API key saved successfully"
                )
            }
        }
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        //Column for align the content in the center
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "settings",
                modifier = Modifier.size(48.dp),
                tint = PrimaryColor,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Create or update your API key for get data from the NY Times",
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(16.dp))

            //TextField for handle user input
            TextField(
                value = apiKeyValue,
                onValueChange = { newApiKey -> apiKeyValue = newApiKey },
                label = { Text("API key") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    cursorColor = PrimaryColor,
                    focusedPlaceholderColor = PrimaryColor,
                    focusedIndicatorColor = PrimaryColor,
                    focusedLabelColor = PrimaryColor,
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            //Button to create or update the api key from the view model
            Button(
                onClick = {
                    settingsViewModel.saveApiKey(apiKey = apiKeyValue)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(PrimaryColor)
            ) {
                Text("Update")
            }

        }
    }

}