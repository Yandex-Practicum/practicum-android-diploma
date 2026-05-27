package ru.practicum.android.diploma.search.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModelImpl : SearchViewModel() {
    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Default)
    override var state: StateFlow<SearchViewState> = _state.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    override fun onQueryChanged(query: String) {
        // no-op: реализация в Epic 1
    }

    override fun onFocusChanged(isFocused: Boolean) {
        // no-op: реализация в Epic 1
    }

    override fun onSearchIconClicked() {
        // no-op: реализация в Epic 1
    }
}
