package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

import ru.practicum.android.diploma.filter.place.domain.model.Region

sealed interface RegionState {
    data class Content(val regions: List<Region>) : RegionState
    object Empty : RegionState
    object Error : RegionState
}
