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
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.PlaceState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState

private const val SEARCH_DEBOUNCE_DELAY = 1000L

class RegionsCountriesViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

//    private val _selectedCountryStateLiveData = MutableLiveData<SelectedCountryState>()
//    fun observeSelectedCountryState(): LiveData<SelectedCountryState> = _selectedCountryStateLiveData

    private val _regionsStateLiveData = MutableLiveData<RegionState>()
    fun observeRegionsState(): LiveData<RegionState> = _regionsStateLiveData

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()
    private val regions: MutableList<Region> = ArrayList<Region>()
    val getRegions: List<Region>
        get() = regions.toList()

    init {
        viewModelScope.launch {
            regionInteractor.listAreas().collect { areas ->
                if (areas.first != null) {
                    places.addAll(areas.first!!)
                    setRegions()
                    val countries = places.map {
                        Country(
                            id = it.id,
                            name = it.name
                        )
                    }
                    _countriesStateLiveData.postValue(CountryState.Content(countries))
                } else {
                    _countriesStateLiveData.postValue(CountryState.Empty)
                }
            }
        }
    }

//    fun setSelectedCountryStateLiveData(state: SelectedCountryState) {
//        _selectedCountryStateLiveData.postValue(state)
//    }

    private fun setRegions() {
        when (val state = _placeStateLiveData.value) {
            is PlaceState.ContentCountry -> getRegions(state.country.id)
            is PlaceState.ContentPlace -> getRegions(state.place.idCountry)
            is PlaceState.Empty, null -> getRegionsAll()
            is PlaceState.Error -> regions.clear()
        }
    }

    fun getRegionsAll() {
        regions.clear()
        places.map { country ->
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

    fun getRegions(countryId: String) {
        regions.clear()
        places.filter {
            countryId == it.id
        }.map { filterCountry ->
            val nameCountry = filterCountry.name
            filterCountry.areas.map { region ->
                region.parentId?.let {
                    regions.add(
                        Region(
                            id = region.id,
                            name = region.name,
                            parentId = region.parentId,
                            parentName = nameCountry
                        )
                    )
                }
            }

        }
        _regionsStateLiveData.postValue(RegionState.Content(regions))
    }

    private var latestSearchText: String? = null

    fun searchRegions(stringSearch: String) {
        _regionsStateLiveData.postValue(RegionState.Loading)
        if (stringSearch.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Default) {
                val regionsList = getRegions.filter {
                    AlgorithmKnuthMorrisPratt.searchSubstringsInString(
                        it.name,
                        stringSearch
                    )
                }
                if (regionsList.isEmpty()) {
                    _regionsStateLiveData.postValue(RegionState.Empty)
                } else {
                    _regionsStateLiveData.postValue(RegionState.Content(regionsList))
                }
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
        if (changedText.isNotEmpty()) {
            searchRegions(changedText)
        }
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText != changedText) {
            latestSearchText = changedText
            trackSearchDebounce(changedText)
        }
    }


    private val _placeStateLiveData = MutableLiveData<PlaceState>()
    fun observePlaceState(): LiveData<PlaceState> = _placeStateLiveData

    fun setPlaceState(placeState: PlaceState) {
        Log.e("setPlaceState", "setPlaceState")
        _placeStateLiveData.postValue(placeState)
    }
}
