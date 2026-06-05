package ru.practicum.android.diploma.presentation.filtration.country.state

import ru.practicum.android.diploma.domain.models.FilterArea

sealed interface ChooseCountryUIState {
    data object Loading : ChooseCountryUIState
    data object Error : ChooseCountryUIState
    data class Content(val countries: List<FilterArea>) : ChooseCountryUIState
}
