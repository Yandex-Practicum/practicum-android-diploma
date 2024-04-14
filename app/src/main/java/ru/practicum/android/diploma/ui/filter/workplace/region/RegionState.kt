package ru.practicum.android.diploma.ui.filter.workplace.region

import ru.practicum.android.diploma.domain.country.Country

sealed interface RegionState {
    data object Loading : RegionState

    data class Empty(
        val message: Int
    ) : RegionState

    data class Content(
        val regions: List<Country>
    ) : RegionState

    data class Error(
        val errorMessage: Int
    ) : RegionState
}
