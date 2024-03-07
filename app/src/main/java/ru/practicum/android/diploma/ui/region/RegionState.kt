package ru.practicum.android.diploma.ui.region

import ru.practicum.android.diploma.domain.country.Country

sealed interface RegionState {
    data object Loading : RegionState

    data class Content(
        val regionId: Country
    ) : RegionState

    data class Error(
        val errorMessage: Int
    ) : RegionState
}
