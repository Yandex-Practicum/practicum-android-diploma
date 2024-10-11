package ru.practicum.android.diploma.filter.place.presentation.viewmodel.state

sealed interface RegionState {
    data class Content(val regions: List<Map<String, String>>) : RegionState
    object Empty : RegionState
    object Error : RegionState
}
