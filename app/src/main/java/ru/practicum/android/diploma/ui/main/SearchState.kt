package ru.practicum.android.diploma.ui.main

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface SearchState {
    data object Loading : SearchState
    data object Empty : SearchState
    data class Content(
        val vacancy : List<Vacancy>?
    ) : SearchState
    data object Error : SearchState
}
