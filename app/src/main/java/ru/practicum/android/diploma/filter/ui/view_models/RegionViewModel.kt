package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse.*
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.AreasUiState
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RegionViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : AreasViewModel(logger, filterInteractor) {



    override fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val countryId = filterInteractor.getSavedFilterSettings(FILTER_KEY).country!!.id
            filterInteractor.getRegions(countryId).collect { state ->
                log("RegionViewModel", "state ${state.thisName}")
                _uiState.value = when (state) {
                    is Success -> AreasUiState.Content(state.data)
                    is NoData  -> AreasUiState.NoData(message = state.message)
                    is Offline -> AreasUiState.Offline(message = state.message)
                    is Error   -> AreasUiState.Error(message = state.message)
                }
            }
        }
    }

    fun saveRegion(region: Region) {
        log(thisName, "saveRegion(region: String)")
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.saveRegion(FILTER_KEY, region)
        }
    }
}