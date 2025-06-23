package ru.practicum.android.diploma.ui.filter.place.models

import ru.practicum.android.diploma.domain.models.Areas

sealed interface RegionState {
    object Loading : RegionState
    object Empty : RegionState

    data class Error(
        val error: Int
    ) : RegionState

    data class Content(
        val countries: List<Areas>
    ) : RegionState
}
