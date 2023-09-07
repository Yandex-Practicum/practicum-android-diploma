package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse.*
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RegionViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : CountryViewModel(filterInteractor, logger) {



    override fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val countryId = filterInteractor.getSavedFilterSettings(FILTER_KEY).country!!.name
            filterInteractor.getRegions(countryId).collect { state ->
                log("RegionViewModel", "getRegions(query).collect { state -> ${state.thisName}")
                _uiState.value = when (state) {
                    is Error   -> FilterScreenState.Error(message = state.message)
                    is Offline -> FilterScreenState.Error(message = state.message)
                    is Success -> FilterScreenState.Content(state.data)
                    is NoData  -> FilterScreenState.NoData(emptyList<Region>(), message = state.message)
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