package ru.practicum.android.diploma.search.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SearchViewModel : ViewModel() {
    abstract var state: StateFlow<SearchScreenState>
    abstract val query: StateFlow<String>
    abstract var isFiltered: StateFlow<Boolean>

    abstract fun onQueryChanged(query: String)
    abstract fun onSearchIconClicked()
}
