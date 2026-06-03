package ru.practicum.android.diploma.presentation.filtration.industry.state

import ru.practicum.android.diploma.domain.models.FilterIndustry

sealed interface IndustryUiState {
    data object Initial : IndustryUiState

    data object Error : IndustryUiState

    data class Content(
        val industries: List<FilterIndustry>,
        val isLoading: Boolean = false,
    ) : IndustryUiState
}
