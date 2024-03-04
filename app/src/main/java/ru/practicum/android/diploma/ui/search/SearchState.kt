package ru.practicum.android.diploma.ui.search

sealed class SearchState {
    object Search : SearchState()
    object Loading : SearchState()
    object Loaded : SearchState() {
        var counter: Int = 0
    }

    object NoInternet : SearchState()
    object ServerError :SearchState()
    object FailedToGetList : SearchState()
    object Start : SearchState()
}
