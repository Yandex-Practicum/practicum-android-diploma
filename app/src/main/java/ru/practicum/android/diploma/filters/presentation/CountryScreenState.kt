package ru.practicum.android.diploma.filters.presentation

import ru.practicum.android.diploma.filters.domain.models.Country

sealed class CountryScreenState {
    data class ContentState(val countryes: List<Country>) : CountryScreenState()
    object EmptyState : CountryScreenState()
}
