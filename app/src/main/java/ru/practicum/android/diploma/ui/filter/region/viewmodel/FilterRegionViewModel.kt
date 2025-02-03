package ru.practicum.android.diploma.ui.filter.region.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasInteractor
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.CountryRegionData
import ru.practicum.android.diploma.domain.models.Places
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterRegionViewModel(
    private val interactor: AreasInteractor
) : ViewModel() {

    private val selectRegionTrigger = SingleEventLiveData<CountryRegionData>()
    fun selectRegionTrigger(): SingleEventLiveData<CountryRegionData> =
        selectRegionTrigger

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Loading)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    private var allRegions: List<CountryRegionData>? = null

    fun onItemClicked(countryId: String, countryName: String, regionId: String, regionName: String) {
        selectRegionTrigger.value = CountryRegionData(
            countryId = countryId,
            countryName = countryName,
            regionId = regionId,
            regionName = regionName
        )
    }

    fun searchRegions(countryId: String?) {
        viewModelScope.launch {
            interactor
                .getRegionsWithCountries(countryId)
                .collect { result ->
                    resultHandle(result)
                }
        }
    }

    fun filterRegions(query: String?) {
        val original = allRegions
        if (original != null) {
            val filteredList = if (query.isNullOrEmpty()) {
                original
            } else {
                original.filter { it.regionName.contains(query, ignoreCase = true) }
            }
            searchResultData.postValue(SearchResult.GetPlacesContent(filteredList))
        }
    }

    private fun resultHandle(result: Resource<Places>) {
        if (result.value != null) {
            val regionMap = result.value.others.associateBy { it.id } + result.value.countries.associateBy { it.id }
            val list = result.value.others.map { Pair(it, findCountry(it, regionMap)) }.map {
                CountryRegionData(
                    it.second.id,
                    it.second.name!!,
                    it.first.id,
                    it.first.name!!
                )
            }
            allRegions = list
            searchResultData.postValue(SearchResult.GetPlacesContent(list))
        } else if (result.errorCode != null) {
            searchResultData.postValue(SearchResult.Error)
        }
    }

    private fun findCountry(region: Area, areas: Map<String, Area>): Area {
        var area = region
        while (area.parentId != null) {
            area = areas[area.parentId]!!
        }
        return area
    }
}
