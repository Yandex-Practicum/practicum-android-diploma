package ru.practicum.android.diploma.presentation.industry

import ru.practicum.android.diploma.domain.models.Industry

sealed interface IndustrySelectionUiState {
    data object Loading : IndustrySelectionUiState
    data object NetworkError : IndustrySelectionUiState
    data object ServerError : IndustrySelectionUiState
    data class Content(
        val industries: List<Industry>,
        val filtered: List<Industry>,
        val searchQuery: String = "",
        val selectedIndustry: Industry? = null,
    ) : IndustrySelectionUiState {
        val canConfirm: Boolean get() = selectedIndustry != null
    }
}
