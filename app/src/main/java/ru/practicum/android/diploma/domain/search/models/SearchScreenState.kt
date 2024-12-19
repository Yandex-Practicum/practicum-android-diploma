package ru.practicum.android.diploma.domain.search.models

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchScreenState {
    object Loading : SearchScreenState()
    data class Content(val foundedVacancies: List<Vacancy>) : SearchScreenState()
    object ServerError : SearchScreenState()
    object NetworkError : SearchScreenState()
    object NotFound : SearchScreenState()
    data class Error(val message: String) : SearchScreenState()
}
