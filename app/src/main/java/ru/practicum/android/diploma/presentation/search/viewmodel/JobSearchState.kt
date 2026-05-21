package ru.practicum.android.diploma.presentation.search.viewmodel

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface JobSearchState {
    data object Initial : JobSearchState

    data object Loading : JobSearchState

    data class Content(
        val found: Int,
        val vacancies: List<Vacancy>,
    ) : JobSearchState
}
