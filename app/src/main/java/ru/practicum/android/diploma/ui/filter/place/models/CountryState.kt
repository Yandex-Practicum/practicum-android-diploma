package ru.practicum.android.diploma.ui.filter.place.models

import ru.practicum.android.diploma.domain.models.Areas

sealed interface CountryState {
    object Loading : CountryState
    object Empty : CountryState

    data class Error(
        val error: Int
    ) : CountryState

    data class Content(
        val countries: List<Areas>
    ) : CountryState
}
