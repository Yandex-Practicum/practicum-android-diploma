package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.BaseFilterScreenState
import ru.practicum.android.diploma.root.BaseViewModel
import javax.inject.Inject

class BaseFilterViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    private val _uiState: MutableStateFlow<BaseFilterScreenState> =
        MutableStateFlow(BaseFilterScreenState.Empty)
    val uiState: StateFlow<BaseFilterScreenState> = _uiState



    fun handleData(coutry: Country?, region: Region?, industry: Industry?) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedFilter = filterInteractor.getSavedFilterSettings(FILTER_KEY)
            _uiState.emit(BaseFilterScreenState.Content(selectedFilter))
            log("WorkPlaceViewModel", "checkSavedFilterData() $selectedFilter")
        }
    }

    companion object {
        const val FILTER_KEY = "filter"
    }
    fun saveIndustry(industry: Industry) {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.saveIndustry(FILTER_KEY, industry)
        }
    }

}