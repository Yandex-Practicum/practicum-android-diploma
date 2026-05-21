package ru.practicum.android.diploma.presentation.search.viewmodel

sealed interface JobSearchState {
    data object Initial : JobSearchState

    data object Loading : JobSearchState

    data object Empty : JobSearchState

    data object Error : JobSearchState

    data class Content(
        val found: Int,
    ) : JobSearchState
}
