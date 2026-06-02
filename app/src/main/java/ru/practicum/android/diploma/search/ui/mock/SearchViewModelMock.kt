package ru.practicum.android.diploma.search.ui.mock

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.search.ui.SearchError
import ru.practicum.android.diploma.search.ui.SearchScreenState
import ru.practicum.android.diploma.search.ui.SearchViewModel

class SearchViewModelMock(
    mockState: SearchScreenState,
    query: String = "",
    isFiltered: Boolean = false,
    errorCode: SearchError? = null
) : SearchViewModel() {
    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)

    override var state: StateFlow<SearchScreenState> = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _isFiltered = MutableStateFlow<Boolean>(false)
    override var isFiltered: StateFlow<Boolean> = _isFiltered.asStateFlow()

    private val _errorCode = MutableSharedFlow<SearchError>()
    override val errorCode: SharedFlow<SearchError> = _errorCode.asSharedFlow()

    init {
        _state.value = mockState
        _query.value = query
        _isFiltered.value = isFiltered
        errorCode?.let {
            _errorCode.tryEmit(it)
        }
    }

    override fun onQueryChanged(query: String) {
        //
    }

    override fun onSearchIconClicked() {}
    override fun onLastItemReached() {}

}
