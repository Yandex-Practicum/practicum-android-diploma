package ru.practicum.android.diploma.search.presenter.model

sealed interface SearchState {

    data class Content(val data: List<VacancyPreviewUi>) : SearchState
    object NotFound : SearchState
    object Loading : SearchState
    object Error : SearchState
    object Empty : SearchState

}
