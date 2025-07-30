package ru.practicum.android.diploma.search.presenter.model

sealed interface SearchState {

    data class Content(val data: List<VacancyPreviewUi>) : SearchState
    data object LoadingMore : SearchState
    data object NotFound : SearchState
    data object Loading : SearchState
    data object Error : SearchState
    data class NoInternet(val isPaginationError: Boolean) : SearchState
    data object Empty : SearchState

}
