package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.commonutils.debounce
import ru.practicum.android.diploma.commonutils.searchsubstring.AlgorithmKnuthMorrisPratt
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState

private const val NUMBER_OF_CACHE_READ_ATTEMPTS = 10
private const val DELAY_BETWEEN_CACHE_READS = 100L
private const val SEARCH_DEBOUNCE_DELAY = 1000L

internal class RegionViewModel(
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
        viewModelScope.launch(Dispatchers.Default) {
            regionInteractor.getPlaceDataFilterReserveBuffer()?.let { place ->
                val idCountry = place.idCountry
                readDataFromCache(idCountry)
            } ?: run {
                readDataFromCache()
            }
        }
    }

    private suspend fun readDataFromCache(idCountry: String? = null) {
        var attempts = 0
        var regionsFetched = false
        _regionsStateLiveData.postValue(RegionState.Loading)
        while (attempts < NUMBER_OF_CACHE_READ_ATTEMPTS && !regionsFetched) {
            regionInteractor.getCountriesCache()?.let { list ->
                places.addAll(list)
                if (idCountry != null) {
                    getRegions(idCountry)
                } else {
                    getRegionsAll()
                }
                regionsFetched = true
            }
            attempts++
            delay(DELAY_BETWEEN_CACHE_READS)
        }
        if (!regionsFetched) {
            _regionsStateLiveData.postValue(RegionState.Error)
        }
    }

    fun setPlaceInDataReserveFilter(place: Place) {
        viewModelScope.launch {
            regionInteractor.updatePlaceInDataFilterReserveBuffer(place)
        }
    }

    private fun updateRegions(filter: (AreaInReference) -> Boolean) {
        regions.clear()
        places.filter(filter).map { country ->
            val nameCountry = country.name
            country.areas.map { region ->
                region.parentId.let {
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
        if (regions.isEmpty()) {
            _regionsStateLiveData.postValue(RegionState.Empty)
        } else {
            _regionsStateLiveData.postValue(RegionState.Content(regions))
        }
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

    private val searchRegionsDebounce = debounce<String>(
        SEARCH_DEBOUNCE_DELAY,
        viewModelScope,
        true,
        false
    ) { changedText ->
        searchRegions(changedText)
    }

    fun searchDebounce(changedText: String) {
        latestSearchText = changedText
        searchRegionsDebounce(changedText)
    }
}
