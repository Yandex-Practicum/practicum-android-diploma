package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.PlaceUiState
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableSharedFlow<SelectedFilter> = MutableSharedFlow()
    val uiState: SharedFlow<SelectedFilter> = _uiState


    fun checkSavedFilterData() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedFilter = filterInteractor.getSavedFilterSettings(FILTER_KEY)
            _uiState.emit(selectedFilter)
            log("WorkPlaceViewModel", "checkSavedFilterData() $selectedFilter")
        }
    }

    fun saveRegion(region: Region?) {
        log(thisName, "saveRegion(region: String)")
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.saveRegion(FILTER_KEY, region)
        }
    }

}