package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class BaseFilterViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableSharedFlow<BaseFilterScreenState> = MutableSharedFlow()
    val uiState: SharedFlow<BaseFilterScreenState> = _uiState

    fun setApplyScreenState() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(BaseFilterScreenState.Apply)
        }
    }

    fun setEmptyScreenState() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.emit(BaseFilterScreenState.Empty)
        }
    }

    fun checkSavedFilterData() {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedFilter = filterInteractor.getSavedFilterSettings(FILTER_KEY)
            _uiState.emit(BaseFilterScreenState.Content(selectedFilter))
            log("WorkPlaceViewModel", "checkSavedFilterData() $selectedFilter")
        }
    }

    companion object {
        const val FILTER_KEY = "filter"
    }

}