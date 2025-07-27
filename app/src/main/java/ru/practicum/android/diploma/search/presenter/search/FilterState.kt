package ru.practicum.android.diploma.search.presenter.search

sealed interface FilterState {
    data object Empty : FilterState
    data object Saved : FilterState
}
