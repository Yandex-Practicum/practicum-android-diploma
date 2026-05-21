package ru.practicum.android.diploma.presentation.search.state

import ru.practicum.android.diploma.domain.models.Vacancy

sealed interface JobSearchState {
    data object Initial : JobSearchState
    data class Content(
        val found: Int,
        val vacancies: List<Vacancy>,
        val isLoading: Boolean = false
    ) : JobSearchState
}
