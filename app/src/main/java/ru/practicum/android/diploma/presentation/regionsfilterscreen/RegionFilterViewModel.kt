package ru.practicum.android.diploma.presentation.regionsfilterscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.util.Resource
import java.io.IOException

class RegionFilterViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {
    private val _regionState = MutableLiveData<RegionsFiltersUiState>()
    val getRegionState: LiveData<RegionsFiltersUiState> = _regionState

    private var regionsList: List<Region> = emptyList()

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
                    resource.data.forEachIndexed { i, city ->
                        Log.d("CitiesDebug", "${i + 1}. ${city.name}")
                    }
                    regionsList = resource.data ?: emptyList()
                    RegionsFiltersUiState.Content(resource.data)
                }

                is Resource.Error -> {
                    RegionsFiltersUiState.Error
                }
            }
        )
    }

    fun search(query: String) {
        try {
            if (regionsList.isEmpty()) {
                _regionState.postValue(RegionsFiltersUiState.Error)
            }

            val filtered = regionsList.filter {
                it.name.contains(query, ignoreCase = true)
            }

            if (filtered.isEmpty()) {
                _regionState.postValue(RegionsFiltersUiState.Empty)
            } else {
                _regionState.postValue(RegionsFiltersUiState.Content(filtered))
            }
        } catch (e: IOException) {
            Log.e("Region", "Search exception")
            _regionState.postValue(RegionsFiltersUiState.Error)
        }
    }
}
