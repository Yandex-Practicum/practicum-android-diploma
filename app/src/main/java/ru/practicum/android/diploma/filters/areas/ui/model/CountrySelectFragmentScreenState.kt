package ru.practicum.android.diploma.filters.areas.ui.model

import ru.practicum.android.diploma.filters.areas.domain.models.Area

sealed class CountrySelectFragmentScreenState {
    data class ChooseItem(val items: List<Area>) : CountrySelectFragmentScreenState()
    data object ServerError : CountrySelectFragmentScreenState()
    data object NetworkError : CountrySelectFragmentScreenState()
    data object Empty : CountrySelectFragmentScreenState()
}
