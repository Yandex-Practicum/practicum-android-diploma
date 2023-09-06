package ru.practicum.android.diploma.filter.ui.view_models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<FilterScreenState> =
        MutableStateFlow(FilterScreenState.Default)
    val uiState: StateFlow<FilterScreenState> = _uiState


}