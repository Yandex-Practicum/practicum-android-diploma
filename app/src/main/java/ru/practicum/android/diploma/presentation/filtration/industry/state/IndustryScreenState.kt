package ru.practicum.android.diploma.presentation.filtration.industry.state

sealed interface IndustryScreenState {
    data object Initial : IndustryScreenState

    data object Error : IndustryScreenState

    data class Content(
        val isLoading: Boolean = false,
    ) : IndustryScreenState
}
