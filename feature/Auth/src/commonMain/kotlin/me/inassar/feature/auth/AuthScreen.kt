package me.inassar.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.inassar.core.navigation.FeedRoute

@Composable
fun AuthScreen(onNavigateFeed: (FeedRoute) -> Unit) {

    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome Auth!")
        OutlinedTextField(
            value = name,
            onValueChange = { newValue -> name = newValue },
            label = { Text("Name") },
            placeholder = { Text("Enter your name") },
            singleLine = true,
            keyboardActions = KeyboardActions(
                onDone = {
                    onNavigateFeed(FeedRoute)
                }
            )
        )
        Button(onClick = { onNavigateFeed(FeedRoute) }) {
            Text("Login")
        }
    }
}
