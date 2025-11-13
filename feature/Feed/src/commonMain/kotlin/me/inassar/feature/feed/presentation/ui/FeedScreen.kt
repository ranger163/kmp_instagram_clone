package me.inassar.feature.feed.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.inassar.feature.feed.presentation.manipulator.FeedEvent
import me.inassar.feature.feed.presentation.manipulator.FeedState
import me.inassar.feature.feed.presentation.manipulator.FeedViewmodel
import org.koin.compose.koinInject

@Composable
fun FeedScreen(viewmodel: FeedViewmodel = koinInject()) {
    val state = viewmodel.state.collectAsStateWithLifecycle().value
    RenderUi(state = state, onAction = viewmodel::onAction)
}

@Composable
fun RenderUi(
    state: FeedState,
    onAction: (FeedEvent) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Welcome Feed!")

        if (state.data != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(state.data)
        }

        if (state.error != null) {
            Text(state.error)
            Spacer(modifier = Modifier.height(12.dp))
        }

        Button(onClick = { onAction(if (state.data == null) FeedEvent.RetrieveFeed else FeedEvent.DeleteLocalFeed) }) {
            if (state.isLoading) CircularProgressIndicator(color = Color.White)
            else Text(if (state.data == null)"Ping Backend" else "Delete Local Feed")
        }
    }
}
