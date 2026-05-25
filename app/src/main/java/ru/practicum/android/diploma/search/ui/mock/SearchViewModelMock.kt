package ru.practicum.android.diploma.search.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState

class SearchViewModelMock(
    mockState: SearchViewState,
    query: String = "",
    isFiltered: Boolean = false
) : SearchViewModel() {
    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Default)
    override var state: StateFlow<SearchViewState> = _state.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()
    init {
        _state.value = mockState
        this.query.value = query
        _isFiltered.value = isFiltered
    }

    override fun onQueryChanged(query: String) {
        //
    }

    override fun onFocusChanged(isFocused: Boolean) {
        //
    }

    override fun onSearchIconClicked() {}

}
