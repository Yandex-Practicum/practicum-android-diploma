package ru.practicum.android.diploma.country.ui

import ru.practicum.android.diploma.core.domain.models.Area

enum class CountryError {
    NO_INTERNET,
    ERROR
}
sealed class CountryScreenState {
    object Loading : CountryScreenState()
    class Content(val items: List<Area>) : CountryScreenState()
    class Error(val error: CountryError) : CountryScreenState()
}
