package ru.practicum.android.diploma.ui.main

sealed class SearchState {
    object Loading : SearchState()
    object Empty : SearchState()
    object Content : SearchState()
    object Error : SearchState()
}
