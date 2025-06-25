package ru.practicum.android.diploma.ui.filter.place.models

sealed interface RegionState {
    object Loading : RegionState
    object Empty : RegionState
    object NotFound : RegionState

    data class Error(
        val error: Int
    ) : RegionState

    data class Content(
        val regions: List<Region>
    ) : RegionState
}
