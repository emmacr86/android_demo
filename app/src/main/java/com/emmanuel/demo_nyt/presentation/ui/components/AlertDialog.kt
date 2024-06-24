package com.emmanuel.demo_nyt.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import com.emmanuel.demo_nyt.presentation.theme.PrimaryColor

/**
 * Alert Dialog to display messages to the user.
 *
 * @param showDialog is the state of the alert dialog.
 * @param onDialogDismiss is the event to dismiss the alert dialog.
 * @param message is the message to display to the user.
 * @return a composable that displays an alert dialog.
 */
@Composable
fun ErrorDialog(
    onDialogDismiss: () -> Unit,
    title: String = "An error occurred!",
    message: String
) {
    AlertDialog(
        onDismissRequest = { onDialogDismiss() },
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(
                onClick = { onDialogDismiss() },
                colors = ButtonDefaults.buttonColors(PrimaryColor),
            ) {
                Text(text = "Close")
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
        ),
        containerColor = MaterialTheme.colorScheme.background,
    )
}

@Preview(showBackground = true)
@Composable
fun ErrorDialogPreview() {
    MaterialTheme {
        var showDialog by remember { mutableStateOf(true) }

        if (showDialog) {
            ErrorDialog(
                onDialogDismiss = { showDialog = false },
                message = "This is an error message."
            )
        }

        // The Box is used to provide a container for the AlertDialog in the preview
        Box(modifier = Modifier.fillMaxSize()) {
        }
    }
}