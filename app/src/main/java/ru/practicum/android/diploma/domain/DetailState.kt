package ru.practicum.android.diploma.domain

import ru.practicum.android.diploma.domain.models.detail.FullVacancy

sealed interface DetailState {
    object Loading : DetailState
    data class Content(
        val vacancy: FullVacancy,
    ) : DetailState

    data class Error(
        val errorMessage: String
    ) : DetailState
}