package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.models.FilterUiState
import ru.practicum.android.diploma.root.BaseViewModel
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class WorkPlaceViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    logger: Logger
) : BaseViewModel(logger) {

    var country: String = ""
    var region: String = ""

    private val _uiState: MutableStateFlow<FilterUiState> =
        MutableStateFlow(FilterUiState.Default)
    val uiState: StateFlow<FilterUiState> = _uiState

    private fun refreshUI() {
        log(thisName, "refreshUI()")
        if (country.isNotEmpty() && region.isNotEmpty()) _uiState.value = FilterUiState.FullData
        else if (country.isNotEmpty()) _uiState.value = FilterUiState.Country
        else if (region.isNotEmpty()) _uiState.value = FilterUiState.Region
        else _uiState.value = FilterUiState.Empty
    }

    fun getUserDataFromPrefs() {
        viewModelScope.launch {
            country = filterInteractor.getCountryFromPrefs(CountryViewModel.COUNTRY_KEY)
            region = filterInteractor.getRegionFromPrefs(RegionViewModel.REGION_KEY)
            log("WorkPlaceViewModel", "getUserDataFromPrefs() country: $country, region: $region")
            refreshUI()
        }
    }


}