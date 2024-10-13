package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.search.domain.models.VacancySearch

sealed class VacancySearchScreenState {
    data object EmptyScreen : VacancySearchScreenState()
    data object Loading : VacancySearchScreenState()
    data object NetworkError : VacancySearchScreenState()
    data object ServerError : VacancySearchScreenState()
    data object SearchError : VacancySearchScreenState()
    data class Content(val vacancies: List<VacancySearch>, val vacanciesCount: Int) : VacancySearchScreenState()
    data object PaginationLoading : VacancySearchScreenState()
    data object PaginationError : VacancySearchScreenState()
}
