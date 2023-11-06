package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface DetailState {
    object Loading : DetailState
    data class Content(
        val vacancy: Vacancy,
    ) : DetailState

    data class Error(
        val errorMessage: String
    ) : DetailState
}