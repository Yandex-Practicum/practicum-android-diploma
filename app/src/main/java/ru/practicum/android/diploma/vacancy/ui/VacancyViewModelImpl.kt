package ru.practicum.android.diploma.vacancy.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.practicum.android.diploma.vacancy.ui.mock.VacancyDetailsPreview

class VacancyViewModelImpl(val id: String) : VacancyViewModel() {
    private val _state = MutableStateFlow<VacancyDetailsViewState>(
        VacancyDetailsViewState.Data(VacancyDetailsPreview.full)
    )
    override var state: StateFlow<VacancyDetailsViewState> = _state.asStateFlow()
}
