package ru.practicum.android.diploma.filter.ui.view_models

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.api.GetRegionUseCase
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel.Companion.FILTER_KEY
import ru.practicum.android.diploma.root.model.UiState
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class RegionViewModel @Inject constructor(
    private val filterInteractor: FilterInteractor,
    private val useCase: GetRegionUseCase,
    logger: Logger
) : AreasViewModel(logger) {
    
    private var regionList = listOf<Region>()

    override fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val countryId = filterInteractor.getSavedFilterSettings(FILTER_KEY).country!!.id
            useCase.getRegions(countryId).fold(::handleFailure,::handleSuccess)
        }
    }
    
    override fun handleSuccess(list: List<Any>) {
        super.handleSuccess(list)
        regionList = list.map { it as Region }
    }

    override fun onSearchQueryChanged(text: String) {
        val temp = regionList
        _uiState.value = UiState.Content(temp.filter {
            it.name.contains(text, true)
        })
    }
    
    fun saveRegion(region: Region) {
        log(thisName, "saveRegion($region: String)")
        selectedFilter = selectedFilter.copy(region = region)
    }
}