package ru.practicum.android.diploma.ui.main

import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto

sealed interface SearchState {
    data object Loading : SearchState
    data object Empty : SearchState
    data class Content(
        val vacancy : VacancyDto
    ) : SearchState
    data object Error : SearchState
}
