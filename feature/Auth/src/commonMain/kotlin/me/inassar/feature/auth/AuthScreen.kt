package me.inassar.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.inassar.core.navigation.FeedRoute

/**
 * Simple placeholder authentication screen that routes to the feed when the action is tapped.
 */
@Composable
fun AuthScreen(
    onNavigateFeed: (FeedRoute) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome Auth!")
        Button(onClick = { onNavigateFeed(FeedRoute) }) {
            Text("Login")
        }
    }
}
