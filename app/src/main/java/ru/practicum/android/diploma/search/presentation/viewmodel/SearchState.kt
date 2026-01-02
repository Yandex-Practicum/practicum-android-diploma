package ru.practicum.android.diploma.search.presentation.viewmodel

import ru.practicum.android.diploma.search.domain.model.VacancyDetail

sealed interface SearchState {
    object Loading : SearchState
    data class Error(val message: String) : SearchState
    data class Content(val data: List<VacancyDetail>) : SearchState
}
