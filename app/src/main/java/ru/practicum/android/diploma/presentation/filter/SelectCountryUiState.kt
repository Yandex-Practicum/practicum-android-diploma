package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.Country

sealed interface SelectCountryUiState {
    object Loading : SelectCountryUiState
    data class Content(val countries: List<Country>) : SelectCountryUiState
    object Error : SelectCountryUiState
}
