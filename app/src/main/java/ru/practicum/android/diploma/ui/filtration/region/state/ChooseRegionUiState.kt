package ru.practicum.android.diploma.ui.filtration.region.state

import ru.practicum.android.diploma.ui.filtration.region.model.RegionUi

data class ChooseRegionUiState(
    val searchQuery: String = "",
    val regions: List<RegionUi> = emptyList(),
    val isLoading: Boolean = false,
    val isEmptySearchResult: Boolean = false,
    val isError: Boolean = false,
)
