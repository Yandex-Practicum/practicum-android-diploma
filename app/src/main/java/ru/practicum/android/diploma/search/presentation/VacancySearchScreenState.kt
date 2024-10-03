package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.search.domain.models.VacancySearch

sealed interface VacancySearchScreenState {
    data object EmptyScreen : VacancySearchScreenState
    data object Loading : VacancySearchScreenState
    data object NetworkError : VacancySearchScreenState
    data object SearchError : VacancySearchScreenState
    data class Content(val vacancies: List<VacancySearch>) : VacancySearchScreenState
    data object PaginationLoading : VacancySearchScreenState
    data class PaginationError(val message: String) : VacancySearchScreenState
}
