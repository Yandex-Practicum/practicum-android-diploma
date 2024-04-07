package ru.practicum.android.diploma.ui.filter.industries

import ru.practicum.android.diploma.domain.country.Country

sealed interface IndustriesState {
    data object Loading : IndustriesState

    data class Empty(
        val message: Int
    ) : IndustriesState

    data class Content(
        val region: List<Country>
    ) : IndustriesState

    data class Error(
        val errorMessage: Int
    ) : IndustriesState
}
