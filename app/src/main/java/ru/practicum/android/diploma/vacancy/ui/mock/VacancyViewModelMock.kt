package ru.practicum.android.diploma.vacancy.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.vacancy.ui.VacancyDetailsViewState
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

class VacancyViewModelMock(mockState: VacancyDetailsViewState) : VacancyViewModel() {
    private val _state = MutableStateFlow<VacancyDetailsViewState>(VacancyDetailsViewState.Loading)
    override var state: StateFlow<VacancyDetailsViewState> = _state.asStateFlow()

    init {
        _state.value = mockState
    }
}
