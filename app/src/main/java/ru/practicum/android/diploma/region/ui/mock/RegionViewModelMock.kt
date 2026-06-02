package ru.practicum.android.diploma.region.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.region.ui.RegionScreenState
import ru.practicum.android.diploma.region.ui.RegionViewModel

class RegionViewModelMock(
    mockState: RegionScreenState,
    mockQuery: String = ""
) : RegionViewModel() {
    private val _query = MutableStateFlow("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _state = MutableStateFlow<RegionScreenState>(RegionScreenState.Loading)
    override val state: StateFlow<RegionScreenState> = _state.asStateFlow()

    init {
        _state.value = mockState
        _query.value = mockQuery
    }

    override fun onQueryChanged(query: String) {}
    override fun onSearchIconClicked() {}
}
