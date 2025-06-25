package ru.practicum.android.diploma.ui.filter.place.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region
import ru.practicum.android.diploma.ui.filter.place.models.RegionState

class RegionViewModel(
    private val areasInteractor: AreasInteractor,
    country: Country?
) : ViewModel() {
    private val regionsFilterState = MutableLiveData<RegionState>()
    fun observeState(): LiveData<RegionState> = regionsFilterState

    private val regionList: ArrayList<Region> = arrayListOf()
    private var parentCountry: Country? = null

    init {
        render(RegionState.Loading)
        parentCountry = country
        viewModelScope.launch {
            areasInteractor.getAreas().collect { pair ->
                processAreas(pair.first, pair.second)
            }
        }
    }

    private fun processAreas(areas: List<Areas>?, error: Int?) {
        if (areas != null) {
            regionList.clear()
            fillRegionsList(areas)
            regionList.sortBy { it.name }
            if (regionList.isEmpty()) {
                render(RegionState.Empty)
            } else {
                render(RegionState.Content(regionList))
            }
        }
        if (error != null) {
            render(RegionState.Error(error))
        }
    }

    private fun fillRegionsList(areas: List<Areas>) {
        val regionByCountry = if (parentCountry != null) {
            areas.find { it.id == parentCountry?.id }?.areas
        } else {
            areas
        }
        regionByCountry?.let {
            for (region in it) {
                allRegionToList(region)
            }
        }
    }

    private fun allRegionToList(area: Areas) {
        if (area.parentId == null) {
            parentCountry = Country(
                id = area.id,
                name = area.name
            )
        }
        if (area.areas.isNotEmpty()) {
            for (areaElement in area.areas) {
                allRegionToList(areaElement)
            }
        } else {
            regionList.add(
                Region(
                    id = area.id,
                    name = area.name,
                    country = parentCountry
                )
            )
        }
    }

    fun onFiltered(text: String) {
        val filteredList = regionList.filter { it.name.contains(text, ignoreCase = true) }
        if (filteredList.isEmpty()) {
            render(RegionState.NotFound)
        } else {
            render(RegionState.Content(filteredList))
        }
    }

    private fun render(state: RegionState) {
        regionsFilterState.postValue(state)
    }

}
