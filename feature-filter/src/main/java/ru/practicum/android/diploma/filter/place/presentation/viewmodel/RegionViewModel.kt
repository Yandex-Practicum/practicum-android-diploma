package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.commonutils.searchsubstring.AlgorithmKnuthMorrisPratt
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState

private const val SEARCH_DEBOUNCE_DELAY = 1000L

class RegionViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val _regionsStateLiveData = MutableLiveData<RegionState>()
    fun observeRegionsState(): LiveData<RegionState> = _regionsStateLiveData

    private val _placeStateLiveData1 = MutableLiveData<PlaceState>()
    fun observePlaceState(): LiveData<PlaceState> = _placeStateLiveData1

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()
    private val regions: MutableList<Region> = ArrayList<Region>()

    private val getRegions: List<Region>
        get() = regions.toList()

    fun initDataFromCacheAndSp() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.getPlaceDataFilterBuffer()?.let { place ->
                val idCountry = place.idCountry
                regionInteractor.getCountriesCache()?.let { list ->
                    places.addAll(list)
                    regions.clear()
                    if (idCountry != null) {
                        getRegions(idCountry)
                    } else {
                        getRegionsAll()
                    }
                    _regionsStateLiveData.postValue(RegionState.Content(regions))
                } ?: run {
                    _regionsStateLiveData.postValue(RegionState.Empty)
                }
            } ?: run {
                _regionsStateLiveData.postValue(RegionState.Error)
            }
        }
    }

    fun setPlaceInDataFilter(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.updatePlaceInDataFilterBuffer(place)
        }
    }

    private fun updateRegions(filter: (AreaInReference) -> Boolean) {
        regions.clear()
        places.filter(filter).map { country ->
            val nameCountry = country.name
            country.areas.map { region ->
                region.parentId?.let {
                    regions.add(
                        Region(
                            id = region.id,
                            name = region.name,
                            parentId = it,
                            parentName = nameCountry
                        )
                    )
                }
            }

        }
        _regionsStateLiveData.postValue(RegionState.Content(regions))
    }

    fun getRegionsAll() {
        updateRegions { true }
    }

    fun getRegions(countryId: String) {
        updateRegions { it.id == countryId }
    }

    private var latestSearchText: String? = null

    private fun searchRegions(stringSearch: String) {
        _regionsStateLiveData.postValue(RegionState.Loading)
        if (stringSearch.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                val regionsList = getRegions.filter {
                    AlgorithmKnuthMorrisPratt.searchSubstringsInString(
                        it.name,
                        stringSearch
                    )
                }
                _regionsStateLiveData.postValue(
                    if (regionsList.isEmpty()) {
                        RegionState.Empty
                    } else {
                        RegionState.Content(regionsList)
                    }
                )
            }
        } else {
            _regionsStateLiveData.postValue(RegionState.Content(getRegions))
        }
    }

    private val trackSearchDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true,
        false
    ) { changedText ->
        searchRegions(changedText)
    }

    fun searchDebounce(changedText: String) {
        latestSearchText = changedText
        trackSearchDebounce(changedText)
    }
}
