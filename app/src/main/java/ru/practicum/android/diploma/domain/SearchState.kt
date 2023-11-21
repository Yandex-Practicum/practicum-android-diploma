package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.domain.models.Vacancy


sealed interface SearchState {
    object Loading : SearchState
    data class Content(
        val vacancies: List<Vacancy>,
        val foundValue: Int
    ) : SearchState

    data class Error(
        val errorMessage: String
    ) : SearchState

    data class Empty(
        val message: String
    ) : SearchState

}
