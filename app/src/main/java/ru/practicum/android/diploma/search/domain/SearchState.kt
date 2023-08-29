package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.models.Vacancy

sealed interface SearchState {
    object Loading : SearchState
    data class TrackContent(
        val tracks: List<Vacancy>,
    ) : SearchState

    data class HistroryContent(
        val historyTrack: List<Vacancy>,
    ) : SearchState

    data class Error(
        val errorMessage: String,
    ) : SearchState

    data class Empty(
        val message: String,
    ) : SearchState
}