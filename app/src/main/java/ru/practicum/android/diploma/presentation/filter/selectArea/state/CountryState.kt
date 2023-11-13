package ru.practicum.android.diploma.presentation.filter.selectArea.state

import ru.practicum.android.diploma.domain.models.filter.Country


sealed interface CountryState {
    class Error(val errorText: String) : CountryState
    class Display(val content: List<Country>) : CountryState
}