package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class BaseFilterViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<BaseFilterScreenState> =
        MutableStateFlow(BaseFilterScreenState.Empty)
    val uiState: StateFlow<BaseFilterScreenState> = _uiState
    
    fun saveSalary(text: String) {
        log(thisName, "saveSalary($text: String)")
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.refreshSalary(FILTER_KEY, text)
        }
    }
    
    fun cancelFilterBtnClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.clearFilter(FILTER_KEY)
            _uiState.emit(BaseFilterScreenState.Content(SelectedFilter.empty))
        }
    }
    
    fun handleData() {
        viewModelScope.launch() {
            val selectedFilter = filterInteractor.getSavedFilterSettings(FILTER_KEY)
            if (selectedFilter == SelectedFilter.empty) _uiState.emit(BaseFilterScreenState.Empty)
            else _uiState.emit(BaseFilterScreenState.Content(selectedFilter))
            log("BaseFilterViewModel", "handleData($selectedFilter)")
        }
    }
    
    companion object { const val FILTER_KEY = "filter" }
}