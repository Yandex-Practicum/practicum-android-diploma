package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<SelectedFilter> = MutableStateFlow(SelectedFilter())
    val uiState: StateFlow<SelectedFilter> = _uiState
    
    fun checkSavedFilterData() {
        viewModelScope.launch {
            val selectedFilter = filterInteractor.getSavedFilterSettings(FILTER_KEY)
            _uiState.emit(selectedFilter)
            log("WorkPlaceViewModel", "checkSavedFilterData() $selectedFilter")
        }
    }
}