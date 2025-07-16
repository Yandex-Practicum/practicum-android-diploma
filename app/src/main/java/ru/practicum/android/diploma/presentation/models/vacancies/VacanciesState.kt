package ru.practicum.android.diploma.presentation.models.vacancies

import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

sealed class VacanciesState {
    object Initial : VacanciesState()
    object Loading : VacanciesState()
    object LoadingMore : VacanciesState()
    data class Success(
        val vacancies: List<Vacancy>,
        val currentPage: Int,
        val totalPages: Int,
        val totalFound: Int,
        val hasMore: Boolean
    ) : VacanciesState()
    object Empty : VacanciesState()
    object NoInternet : VacanciesState()
    object ServerError : VacanciesState()
}
