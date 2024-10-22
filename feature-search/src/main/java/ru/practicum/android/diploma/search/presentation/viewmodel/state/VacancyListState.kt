package ru.practicum.android.diploma.search.presentation.viewmodel.state

import ru.practicum.android.diploma.search.domain.models.Vacancy

internal sealed interface VacancyListState {

    data object Loading : VacancyListState

    data class Content(val vacancies: List<Vacancy>) : VacancyListState

    data class Error(val reason: String) : VacancyListState

    data object Empty : VacancyListState
}
