package ru.practicum.android.diploma.presentation.regionsfilterscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.filters.repository.FiltersParametersInteractor
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.util.Resource

class RegionFilterViewModel(
    private val interactor: FiltersInteractor,
    private val parametersInteractor: FiltersParametersInteractor
) : ViewModel() {
    private val _regionState = MutableLiveData<RegionsFiltersUiState>()
    val getRegionState: LiveData<RegionsFiltersUiState> = _regionState

    private var allCountries: List<Country> = emptyList()

    init {
        val countryId = parametersInteractor.getSelectedCountryId() ?:""
        Log.d("Country", countryId)
        loadRegions(countryId)
        loadAllCountries()
    }

    private fun loadAllCountries() {
        viewModelScope.launch {
            interactor.getCountries().collect { resource ->
                if (resource is Resource.Success) {
                    allCountries = resource.data ?: emptyList()
                    Log.d("RegionSelectedCountry", allCountries.toString())
                }
            }
        }
    }

    fun loadRegions(countryId: String) {
        viewModelScope.launch {
            _regionState.postValue(RegionsFiltersUiState.Loading)
            interactor.getRegions(countryId)
                .collect {
                    statusRegion(it)
                }
        }
    }

    private fun statusRegion(resource: Resource<List<Region>>) {
        _regionState.postValue(
            when (resource) {
                is Resource.Success -> {
                    Log.d("CitiesDebug", "Cities loaded successfully: ${resource.data!!.size} items")
                    resource.data!!.forEachIndexed { i, city ->
                        Log.d("CitiesDebug", "${i+1}. ${city.name}")
                    }
                    RegionsFiltersUiState.Content(resource.data!!)
                }
                is Resource.Error -> {
                    RegionsFiltersUiState.Error
                }
            }
        )
    }

    fun onRegionSelected(region: Region) {
        Log.d("RegionSelectedCountry1", allCountries.toString())
        Log.d("RegionSelectedCountry2", region.id)
        var countryName = ""
        for (country in allCountries) {
            if (country.id == region.countryId) {
                countryName = country.name
                break
            }
        }
        parametersInteractor.selectRegion(
            regionName = region.name,
            countryName = countryName
        )
        Log.d("RegionSelected", "Region: ${region.name}, Country: $countryName")
    }
}
