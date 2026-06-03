package ru.practicum.android.diploma.presentation.filtration.industry.state

sealed interface IndustryUiState {
    data object Initial : IndustryUiState

    data object Error : IndustryUiState

    data class Content(
        val isLoading: Boolean = false,
    ) : IndustryUiState
}
