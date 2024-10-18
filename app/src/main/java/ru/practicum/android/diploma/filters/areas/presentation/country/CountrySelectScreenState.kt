package ru.practicum.android.diploma.filters.areas.presentation.country

import ru.practicum.android.diploma.filters.areas.domain.models.Area

sealed class CountrySelectScreenState {
    data class ChooseItem(val items: List<Area>) : CountrySelectScreenState()
    data object ServerError : CountrySelectScreenState()
    data object NetworkError : CountrySelectScreenState()
    data object Empty : CountrySelectScreenState()
}
