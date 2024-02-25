package ru.practicum.android.diploma.ui.search

sealed class SearchState {
    object Search : SearchState()
    object Loading : SearchState()
    object Loaded : SearchState()
    object NoInternet : SearchState()
    object FailedToGetList : SearchState()
}
