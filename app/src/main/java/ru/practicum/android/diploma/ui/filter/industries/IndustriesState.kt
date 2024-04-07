package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.ui.filter.workplace.country.CountryState

sealed interface IndustriesState {
    data object Loading : IndustriesState
    data class Empty(
        val message: Int
    ) : IndustriesState

    data class IndustriesList(
        val industries: List<ChildIndustryWithSelection>
    ) : IndustriesState

    data class FilteredIndustry(
        val industry: ChildIndustryWithSelection
    ) : IndustriesState

    data class Error(
        val errorMessage: Int
    ) : IndustriesState
}
