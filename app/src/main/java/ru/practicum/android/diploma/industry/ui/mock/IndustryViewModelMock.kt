package ru.practicum.android.diploma.industry.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.industry.ui.IndustryScreenState
import ru.practicum.android.diploma.industry.ui.IndustryViewModel
import kotlin.text.isEmpty

class IndustryViewModelMock(
    mockQuery: String,
    mockState: IndustryScreenState
) : IndustryViewModel() {
    private val _query = MutableStateFlow<String>("")
    override val query: StateFlow<String> = _query.asStateFlow()
    private val _state = MutableStateFlow<IndustryScreenState>(IndustryScreenState.Default)
    override var state: StateFlow<IndustryScreenState> = _state.asStateFlow()

    init {
        when (mockState) {
            is IndustryScreenState.Content -> { filter(mockQuery, mockState.industries) }
            is IndustryScreenState.Default -> { }
            is IndustryScreenState.Error -> { }
            is IndustryScreenState.Loading -> { }
        }
    }

    override fun onSearchIconClicked() {}

    override fun onQueryChanged(query: String) {}

    override fun applyFilter(industry: Industry) {}

    private fun filter(query: String, industries: List<Industry>) {
        _query.value = query
        if (!query.isEmpty()) {
            val filterList = industries.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }
            _state.value = IndustryScreenState.Content(filterList)
        } else {
            _state.value = IndustryScreenState.Content(industries)
        }
        _state.value.showClearButton = !query.isEmpty()
    }
}
