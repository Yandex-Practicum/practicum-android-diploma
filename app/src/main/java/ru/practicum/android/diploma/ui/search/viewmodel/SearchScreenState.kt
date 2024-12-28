package ru.practicum.android.diploma.ui.search.viewmodel

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchScreenState {
    data object Loading : SearchScreenState()
    data class Content(val foundedVacancies: List<Vacancy>) : SearchScreenState()
    data object ServerError : SearchScreenState()
    data object NetworkError : SearchScreenState()
    data object NotFound : SearchScreenState()
    data class Error(val message: String) : SearchScreenState()
    data object Empty : SearchScreenState()
}
