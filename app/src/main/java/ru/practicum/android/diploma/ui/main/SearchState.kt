package ru.practicum.android.diploma.ui.main

import ru.practicum.android.diploma.data.dto.responseUnits.VacancyDto
import ru.practicum.android.diploma.domain.models.main.Vacancy

sealed interface SearchState {
    data object Loading : SearchState
    data object Empty : SearchState
    data class Content(
        val vacancy : List<Vacancy>
    ) : SearchState
    data object Error : SearchState
}
