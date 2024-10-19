package ru.practicum.android.diploma.filters.areas.presentation.region

import ru.practicum.android.diploma.filters.areas.domain.models.Area

sealed class RegionSelectScreenState {
    data class ChooseItem(val items: List<Area>) : RegionSelectScreenState()
    data object ServerError : RegionSelectScreenState()
    data object NetworkError : RegionSelectScreenState()
    data object Empty : RegionSelectScreenState()
    data class FilterRequest(val request: String) : RegionSelectScreenState()
    data object Loading : RegionSelectScreenState()
}
