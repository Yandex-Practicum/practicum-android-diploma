package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.ui.models.PlaceUiState
import ru.practicum.android.diploma.filter.ui.models.SelectedData
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    var selectedData = SelectedData()
        private val _uiState: MutableStateFlow<PlaceUiState> = MutableStateFlow(PlaceUiState.Default)
    val uiState: StateFlow<PlaceUiState> = _uiState


    fun refreshUI() {
        viewModelScope.launch(Dispatchers.IO) {
            selectedData = filterInteractor.getSelectedData(FILTER_KEY)
            if (selectedData.country != null) _uiState.value = PlaceUiState.Country
            if (selectedData.region != null) _uiState.value = PlaceUiState.Region
            log("WorkPlaceViewModel", "refreshUI() selectedData=$selectedData")
        }
    }

}