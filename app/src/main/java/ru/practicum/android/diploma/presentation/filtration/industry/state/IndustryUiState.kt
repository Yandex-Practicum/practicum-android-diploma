package ru.practicum.android.diploma.presentation.filtration.industry.state

import ru.practicum.android.diploma.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface IndustryUiState {
    data object Initial : IndustryUiState

    data object Error : IndustryUiState

    data class Content(
        val industries: List<FilterIndustryDto>, // Industry
        val isLoading: Boolean = false,
    ) : IndustryUiState
}
