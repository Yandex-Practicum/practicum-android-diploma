package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.VacanciesList

sealed class SearchVacanciesState {
    data class ShowContent(val content: VacanciesList) : SearchVacanciesState()
    data object Loading : SearchVacanciesState()
    data object NothingFound : SearchVacanciesState()
    data object NetworkError : SearchVacanciesState()
    data object NoInternet : SearchVacanciesState()
    data object Default : SearchVacanciesState()
}
