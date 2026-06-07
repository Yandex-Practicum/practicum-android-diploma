package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.Region

sealed interface SelectRegionUiState {
    object Loading : SelectRegionUiState
    data class Content(
        val regions: List<Region>,
        val query: String
    ) : SelectRegionUiState
    object Error : SelectRegionUiState
    data class EmptySearch(val query: String) : SelectRegionUiState
}
