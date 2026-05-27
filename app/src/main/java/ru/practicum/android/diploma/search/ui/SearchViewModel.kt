package ru.practicum.android.diploma.search.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class SearchViewModel : ViewModel() {
    abstract var state: StateFlow<SearchViewState>
    var query = mutableStateOf("")
    abstract var isFiltered: StateFlow<Boolean>

    abstract fun onQueryChanged(query: String)
    abstract fun onFocusChanged(isFocused: Boolean)
    abstract fun onSearchIconClicked()
}
