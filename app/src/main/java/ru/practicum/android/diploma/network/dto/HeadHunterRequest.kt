package ru.practicum.android.diploma.network.dto

sealed class HeadHunterRequest {
    data object Locales : HeadHunterRequest()
    data object Dictionaries : HeadHunterRequest()
    data object Industries : HeadHunterRequest()
    data object Areas : HeadHunterRequest()
    data object Counties : HeadHunterRequest()
    data class VacancySuggestions(val textForSuggestions: String) : HeadHunterRequest()
}
