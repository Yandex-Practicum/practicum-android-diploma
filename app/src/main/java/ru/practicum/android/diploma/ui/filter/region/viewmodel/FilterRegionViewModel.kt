package ru.practicum.android.diploma.ui.filter.region.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.areas.api.AreasInteractor
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Places
import ru.practicum.android.diploma.util.SingleEventLiveData

data class CountryRegionData(
    val countryId: String,
    val countryName: String,
    val regionId: String,
    val regionName: String
)

class FilterRegionViewModel(
    private val interactor: AreasInteractor
) : ViewModel() {

    private val selectRegionTrigger = SingleEventLiveData<CountryRegionData>()
    fun selectRegionTrigger(): SingleEventLiveData<CountryRegionData> =
        selectRegionTrigger

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Loading)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

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

    private fun resultHandle(result: Resource<Places>) {
        if (result.value != null) {
            searchResultData.postValue(SearchResult.GetPlacesContent(result.value.countries, result.value.others))

        } else if (result.errorCode != null) {
            searchResultData.postValue(SearchResult.Error)
        }
    }
}
