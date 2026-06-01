package ru.practicum.android.diploma.vacancy.ui.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.vacancy.ui.VacancyState
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

class VacancyViewModelMock(mockState: VacancyState) : VacancyViewModel() {
    private val _state = MutableStateFlow(mockState)
    override val state: StateFlow<VacancyState> = _state.asStateFlow()

    override fun onFavoriteClicked() = Unit
}
