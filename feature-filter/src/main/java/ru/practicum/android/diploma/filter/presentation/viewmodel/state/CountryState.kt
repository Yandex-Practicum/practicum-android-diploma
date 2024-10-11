package ru.practicum.android.diploma.filter.presentation.viewmodel.state

sealed interface CountryState {
    data class Content(val coutries: List<Country>) : CountryState
    object Empty : CountryState
    object Error : CountryState
}
