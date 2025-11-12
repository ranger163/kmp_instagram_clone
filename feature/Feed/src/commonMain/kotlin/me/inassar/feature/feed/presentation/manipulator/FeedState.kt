package me.inassar.feature.feed.presentation.manipulator
/**
 * UI State that represents FeedScreen
 **/
data class FeedState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: String? = null
)

//sealed interface SampleState{
//    data object Empty: SampleState
//    data object Loading: SampleState
//    data class Success(val data: String?): SampleState
//    data class Error(val error: String?): SampleState
//}