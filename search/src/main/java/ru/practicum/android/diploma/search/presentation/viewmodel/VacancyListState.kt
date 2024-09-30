package ru.practicum.android.diploma.search.presentation.viewmodel

import ru.practicum.android.diploma.search.domain.models.VacancyDetail

sealed interface VacancyListState {

    object Loading : VacancyListState

    data class Content(val vacancyDetail: VacancyDetail) : VacancyListState

    data class Error(val reason: String) : VacancyListState

    object Empty : VacancyListState
}
