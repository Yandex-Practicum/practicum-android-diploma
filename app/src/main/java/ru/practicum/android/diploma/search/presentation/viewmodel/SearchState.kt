package ru.practicum.android.diploma.search.presentation.viewmodel

import ru.practicum.android.diploma.core.presentation.ui.model.VacancyListItemUi

sealed interface SearchState {
    object Loading : SearchState
    object Nothing : SearchState
    data class Error(val message: String) : SearchState
    data class Content(val data: List<VacancyListItemUi>, val isLoading: Boolean = false) :
        SearchState
}
