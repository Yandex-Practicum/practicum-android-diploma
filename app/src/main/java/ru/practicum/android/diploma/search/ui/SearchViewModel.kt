package ru.practicum.android.diploma.search.ui

import kotlinx.coroutines.flow.StateFlow

interface SearchViewModel {
    val state: StateFlow<SearchScreenState>
    val query: StateFlow<String>
    val isFiltered: StateFlow<Boolean>

    fun onQueryChanged(newText: String)
    fun onFocusChanged(hasFocus: Boolean)
    fun onSearchIconClicked()
}
