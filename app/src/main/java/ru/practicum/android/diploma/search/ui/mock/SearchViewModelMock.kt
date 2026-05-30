package ru.practicum.android.diploma.search.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.search.ui.SearchScreenState
import ru.practicum.android.diploma.search.ui.SearchViewModel

class SearchViewModelMock(
    mockState: SearchScreenState,
    query: String = "",
    isFiltered: Boolean = false
) : SearchViewModel() {
    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Default)
    override var state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()
    init {
        _state.value = mockState
        _query.value = query
        _isFiltered.value = isFiltered
    }

    override fun onQueryChanged(query: String) {
        //
    }

    override fun onSearchIconClicked() {}

}
