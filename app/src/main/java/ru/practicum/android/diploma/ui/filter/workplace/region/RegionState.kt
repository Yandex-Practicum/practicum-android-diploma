package ru.practicum.android.diploma.ui.filter.workplace.region

import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country

sealed interface RegionState {
    data object Loading : RegionState

    data class Empty(
        val message: Int
    ) : RegionState

    data class Content(
        val regions: List<Country>
    ) : RegionState

    enum class Error(val errorMessage: Int) : RegionState {
        SERVER_ERROR(R.string.server_error),
        NOTHING_FOUND(R.string.nothing_found),
        NO_CONNECTION(R.string.no_internet)
    }
}
