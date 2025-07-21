package ru.practicum.android.diploma.presentation.regionsfilterscreen

import ru.practicum.android.diploma.domain.models.filters.Region

sealed class RegionsFiltersUiState {
    data object Loading : RegionsFiltersUiState()
    data class Content(val regions: List<Region>) : RegionsFiltersUiState()
    data object Error : RegionsFiltersUiState()
    data object Empty : RegionsFiltersUiState()
}
