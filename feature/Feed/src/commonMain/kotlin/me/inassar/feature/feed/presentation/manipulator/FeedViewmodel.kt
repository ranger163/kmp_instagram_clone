package me.inassar.feature.feed.presentation.manipulator

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.feature.feed.presentation.mapper.toUi
import me.inassar.shared.helpers.DispatcherProvider
import me.inassar.shared.helpers.PlatformCapabilitiesProvider

/**
 * Presentation-layer state holder for the feed screen.
 *
 * @property repo Repository providing feed data.
 * @property dispatcher Dispatcher provider to scope coroutine work.
 * @param platformCapabilities Supplies capability metadata to seed the initial state.
 */
@Stable
class FeedViewmodel(
    private val repo: FeedRepository,
    platformCapabilities: PlatformCapabilitiesProvider,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    private val _state: MutableStateFlow<FeedState> =
        MutableStateFlow(FeedState(capabilities = platformCapabilities.getCapabilities()))

    /** Publicly exposed immutable view state for the UI. */
    val state: StateFlow<FeedState> = _state.asStateFlow()

    /** Dispatches UI events to their corresponding handlers. */
    fun onAction(action: FeedEvent) {
        when (action) {
            FeedEvent.RetrieveFeed -> validateBackend()
            FeedEvent.DeleteLocalFeed -> deleteLocalFeed()
        }
    }

    /** Runs the feed retrieval pipeline and updates state accordingly. */
    private fun validateBackend() {
        _state.update { it.copy(isLoading = true, data = null, error = null) }

        viewModelScope.launch(dispatcher.default) {
            val result = repo.retrieveFeed()
            result
                .onSuccess { data ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = message(fromCache = data.fromCache, response = data.toUi().response),
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = "Error: ${error.message}", data = null)
                    }
                }
        }
    }

    /** Clears the cached feed and resets UI state. */
    private fun deleteLocalFeed() = viewModelScope.launch(dispatcher.default) {
        repo.deleteLocalFeed()
        _state.update { it.copy(data = null, isLoading = false, error = null) }
    }

    /** Formats the UI message depending on whether the payload came from cache or network. */
    private fun message(fromCache: Boolean, response: String): String = when (fromCache) {
        true -> "Data retrieved from cache\nLocal data: $response"
        false -> "Cache empty, fetching from remote\nRemote data: $response"
    }
}
