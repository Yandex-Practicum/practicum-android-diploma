package ru.practicum.android.diploma.domain.models

sealed class SearchVacanciesState {
    data object Loading : SearchVacanciesState()
    data class VacanciesList(val vacancies: List<Vacancy>) : SearchVacanciesState()
    data object NetworkError : SearchVacanciesState()
    data object NothingFound : SearchVacanciesState()
    data object Empty : SearchVacanciesState()

}
