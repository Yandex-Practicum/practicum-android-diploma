package ru.practicum.android.diploma.filter.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.filter.ui.FilterScreenState
import ru.practicum.android.diploma.filter.ui.FilterViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewState

class FilterViewModelMock(
    mockState: FilterScreenState,
) : FilterViewModel() {
    private val _state = MutableStateFlow<FilterScreenState>(FilterScreenState())
    override var state: StateFlow<FilterScreenState> = _state.asStateFlow()
    init {
        _state.value = mockState
    }
    override fun onQueryChanged(query: String) {}

    override fun onFocusChanged(focus: Boolean) {}

    override fun onResetArea() {}

    override fun onResetIndustry() {}

    override fun onToggleSalary() {}
}
