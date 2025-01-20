package ru.practicum.android.diploma.search.presentation.state

import ru.practicum.android.diploma.search.domain.model.Vacancy

sealed interface SearchScreenState {
    data object Loading: SearchScreenState

    data class Content(
        val vacancies: List<Vacancy>
    ): SearchScreenState

    data class Empty (
        val message: String
    ): SearchScreenState
}
