package ru.practicum.android.diploma.search.ui

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.utils.VacanciesData

sealed class SearchState {
    data class Loading(val isNewPage: Boolean) : SearchState()
    data class Content(val results: List<Vacancy>, val foundVacancies: Int) : SearchState()
    data class Error(val error: VacanciesData.VacanciesSearchError) : SearchState()
    data object Empty : SearchState()
}
