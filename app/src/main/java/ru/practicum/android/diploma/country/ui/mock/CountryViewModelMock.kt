package ru.practicum.android.diploma.country.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.country.ui.CountryScreenState
import ru.practicum.android.diploma.country.ui.CountryViewModel

class CountryViewModelMock(
    mockState: CountryScreenState,
) : CountryViewModel() {
    private val _state = MutableStateFlow<CountryScreenState>(CountryScreenState.Loading)
    override var state: StateFlow<CountryScreenState> = _state.asStateFlow()

    init {
        _state.value = mockState
    }

}
