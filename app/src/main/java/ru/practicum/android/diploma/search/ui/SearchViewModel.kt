package ru.practicum.android.diploma.search.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class SearchViewModel : ViewModel() {
    abstract var state: StateFlow<SearchScreenState>
    abstract val query: StateFlow<String>
    abstract var isFiltered: StateFlow<Boolean>
    abstract val errorCode: SharedFlow<SearchError?>

    abstract fun onQueryChanged(newText: String)
    abstract fun onSearchIconClicked()
    abstract fun onLastItemReached()
}
