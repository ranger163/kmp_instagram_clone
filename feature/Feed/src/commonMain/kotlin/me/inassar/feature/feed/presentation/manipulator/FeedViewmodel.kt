package me.inassar.feature.feed.presentation.manipulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.inassar.feature.feed.domain.repository.FeedRepository
import me.inassar.feature.feed.presentation.mapper.toUi

class FeedViewmodel(private val repo: FeedRepository) : ViewModel() {
    private val _state: MutableStateFlow<FeedState> = MutableStateFlow(FeedState())
    val state: StateFlow<FeedState> = _state.asStateFlow()

    fun onAction(action: FeedEvent) {
        when (action) {
            FeedEvent.PingBackend -> validateBackend()
        }
    }

    private fun validateBackend() {
        _state.update { it.copy(isLoading = true, data = null, error = null) }

        viewModelScope.launch {
            val result = repo.pingBackend()
            result
                .onSuccess { data ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            data = data.toUi().response,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _state.update {
                        it.copy(isLoading = false, error = "Backend error: ${error.message}", data = null)
                    }
                }
        }
    }
}
