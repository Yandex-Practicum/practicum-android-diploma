package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Region

internal sealed interface RegionState {
    data object Loading : RegionState
    data class Content(val regions: List<Region>) : RegionState
    data object Empty : RegionState
    data object Error : RegionState
}
