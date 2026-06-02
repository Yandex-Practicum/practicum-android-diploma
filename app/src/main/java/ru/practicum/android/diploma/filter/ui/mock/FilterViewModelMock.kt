package ru.practicum.android.diploma.filter.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.filter.ui.FilterViewModel

class FilterViewModelMock(
    mockState: Filters,
) : FilterViewModel() {
    private val _state = MutableStateFlow<Filters>(Filters())
    override var state: StateFlow<Filters> = _state.asStateFlow()

    private val _isModified = MutableStateFlow(true)
    override val isModified: StateFlow<Boolean> = _isModified.asStateFlow()

    init {
        _state.value = mockState
    }
    override fun onQueryChanged(query: String) {}
    override fun onResetSalary() {}

    override fun onResetArea() {}

    override fun onResetIndustry() {}

    override fun onToggleSalary() {}
    override fun apply() {}

    override fun cancel() {}
    override fun reset() {}
}
