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

@Stable
class FeedViewmodel(
    private val repo: FeedRepository,
    platformCapabilities: PlatformCapabilitiesProvider,
    private val dispatcher: DispatcherProvider
) : ViewModel() {
    private val _state: MutableStateFlow<FeedState> =
        MutableStateFlow(FeedState(capabilities = platformCapabilities.getCapabilities()))
    val state: StateFlow<FeedState> = _state.asStateFlow()

    fun onAction(action: FeedEvent) {
        when (action) {
            FeedEvent.RetrieveFeed -> validateBackend()
            FeedEvent.DeleteLocalFeed -> deleteLocalFeed()
        }
    }

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

    private fun deleteLocalFeed() = viewModelScope.launch(dispatcher.default) {
        repo.deleteLocalFeed()
        _state.update { it.copy(data = null, isLoading = false, error = null) }
    }

    private fun message(fromCache: Boolean, response: String): String = when (fromCache) {
        true -> "Data retrieved from cache\nLocal data: $response"
        false -> "Cache empty, fetching from remote\nRemote data: $response"
    }
}
