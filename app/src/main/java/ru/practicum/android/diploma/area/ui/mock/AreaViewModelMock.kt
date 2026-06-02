package ru.practicum.android.diploma.area.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.area.ui.AreaScreenState
import ru.practicum.android.diploma.area.ui.AreaViewModel
import ru.practicum.android.diploma.core.domain.models.Area

class AreaViewModelMock(
    mockState: AreaScreenState,
) : AreaViewModel() {
    private val _state = MutableStateFlow<AreaScreenState>(AreaScreenState())
    override var state: StateFlow<AreaScreenState> = _state.asStateFlow()

    init {
        _state.value = mockState
    }

    override fun resetCountry() {}

    override fun resetRegion() {}
    override fun apply() {}
    override fun onBack(country: Area?) {}

}
