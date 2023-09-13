package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class BaseFilterViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    var selectedFilter: SelectedFilter = SelectedFilter.empty
    private val _uiState: MutableStateFlow<BaseFilterScreenState> =
        MutableStateFlow(BaseFilterScreenState.Empty)
    val uiState: StateFlow<BaseFilterScreenState> = _uiState

    
    fun handleData(data: SelectedFilter) {
        viewModelScope.launch {
            selectedFilter = data

            if (selectedFilter == SelectedFilter.empty) _uiState.emit(BaseFilterScreenState.Empty)
            else _uiState.emit(BaseFilterScreenState.Content(selectedFilter))
            log("BaseFilterViewModel", "handleData($selectedFilter)")
        }
    }

    fun changeSalary(text: String?) {
        selectedFilter =
            if (text.isNullOrEmpty()) selectedFilter.copy(salary = null)
            else selectedFilter.copy(salary = text)
        log(thisName, "saveSalary($text: String)")
    }

    fun saveFilterSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.saveFilterSettings(key = FILTER_KEY, data = selectedFilter)
        }
    }

    fun cancelFilterBtnClicked() {
        selectedFilter = SelectedFilter.empty
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.clearFilter(FILTER_KEY)
            _uiState.emit(BaseFilterScreenState.Content(SelectedFilter.empty))
        }
    }
    
    companion object { const val FILTER_KEY = "filter" }
}