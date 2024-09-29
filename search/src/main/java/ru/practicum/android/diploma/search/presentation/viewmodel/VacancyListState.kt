package ru.practicum.android.diploma.search.presentation.viewmodel

import ru.practicum.android.diploma.search.domain.Vacancy

sealed interface VacancyListState {

    object Loading : VacancyListState

    data class Content(val vacancies: List<Vacancy>) : VacancyListState

    data class Error(val reason: Int) : VacancyListState

    object Empty : VacancyListState
}
