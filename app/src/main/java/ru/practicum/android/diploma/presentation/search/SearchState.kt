package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.models.Vacancy

sealed class SearchState {
    object Default : SearchState()
    object NoInternet : SearchState()
    object NoResults : SearchState()
    object Loading : SearchState()
    data class Success(val vacancies: List<Vacancy>, val totalVacancies: Int) : SearchState()
    data class Error(val message: String) : SearchState()
}
