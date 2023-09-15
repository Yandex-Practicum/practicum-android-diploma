package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    logger: Logger
) : BaseViewModel(logger) {

    var selectedFilter: SelectedFilter = SelectedFilter.empty
    private val _uiState: MutableStateFlow<SelectedFilter> = MutableStateFlow(SelectedFilter())
    val uiState: StateFlow<SelectedFilter> = _uiState
    
    fun handleInputArgs(data: SelectedFilter) {
        selectedFilter = data
        viewModelScope.launch {
            _uiState.emit(selectedFilter)
            log("WorkPlaceViewModel", "checkSavedFilterData() $selectedFilter")
        }
    }

    fun changeCountry() {
        selectedFilter = selectedFilter.copy(country = null)
        log(thisName, "changeCountry: country = null")
    }

    fun changeRegion() {
        selectedFilter = selectedFilter.copy(region = null)
        log(thisName, "changeRegion: region = null")
    }
}