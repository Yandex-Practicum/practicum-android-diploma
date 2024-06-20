package ru.practicum.android.diploma.presentation.search

import ru.practicum.android.diploma.domain.search.models.DomainVacancy

sealed class SearchState {
    object Default : SearchState()
    object NoInternet : SearchState()
    object NoResults : SearchState()
    object Loading : SearchState()
    data class Success(val vacancies: List<DomainVacancy>, val totalVacancies: Int) : SearchState()
    data class Error(val message: String) : SearchState()
}
