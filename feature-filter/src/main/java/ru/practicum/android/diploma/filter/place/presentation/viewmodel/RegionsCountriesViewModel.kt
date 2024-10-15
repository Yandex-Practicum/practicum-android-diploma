package ru.practicum.android.diploma.filter.place.presentation.viewmodel

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
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.SelectedCountryState

private const val SEARCH_DEBOUNCE_DELAY = 1000L

class RegionsCountriesViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

    private val _selectedCountryStateLiveData = MutableLiveData<SelectedCountryState>()
    fun observeSelectedCountryState(): LiveData<SelectedCountryState> = _selectedCountryStateLiveData

    private val _regionsStateLiveData = MutableLiveData<RegionState>()
    fun observeRegionsState(): LiveData<RegionState> = _regionsStateLiveData

    private val _placeStateLiveData = MutableLiveData<PlaceState>()
    fun observePlaceState(): LiveData<PlaceState> = _placeStateLiveData

    fun setPlaceState(placeState: PlaceState) {
        _placeStateLiveData.postValue(placeState)
    }

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()
    private val regions: MutableList<Region> = ArrayList<Region>()
    val getRegions: List<Region>
        get() = regions.toList()

    init {
        initData()
    }

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            initDataFromNetworkAndSp()
        }
    }

    private suspend fun initDataFromNetworkAndSp() {
        initDataFromNetwork()
        initDataFromSp()
    }

    private suspend fun initDataFromNetwork() {
        runCatching {
            regionInteractor.listAreas().collect { areas ->
                areas.first?.let { list ->
                    places.addAll(list)
                    val countries = places.map {
                        Country(
                            id = it.id,
                            name = it.name
                        )
                    }
                    _countriesStateLiveData.postValue(CountryState.Content(countries))
                } ?: run {
                    _countriesStateLiveData.postValue(CountryState.Empty)
                }
            }
        }.fold(
            onSuccess = {},
            onFailure = {
                _countriesStateLiveData.postValue(CountryState.Error)
            }
        )

    }

    private suspend fun initDataFromSp() {
        regionInteractor.getPlaceDataFilter()?.let { place ->
            val idCountry = place.idCountry
            val nameCountry = place.nameCountry
            val idRegion = place.idRegion
            val nameRegion = place.nameRegion

            if (idCountry != null && nameCountry != null) {
                val placeState = if (idRegion == null && nameRegion == null) {
                    PlaceState.ContentCountry(Country(idCountry, nameCountry))
                } else {
                    PlaceState.ContentPlace(Place(idCountry, nameCountry, idRegion, nameRegion))
                }
                _placeStateLiveData.postValue(placeState)
                getRegions(idCountry)
            } else {
                _placeStateLiveData.postValue(PlaceState.Error)
                regions.clear()
            }
        } ?: run {
            _placeStateLiveData.postValue(PlaceState.Empty)
            getRegionsAll()
        }
    }

    fun setPlaceInDataFilter(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.updatePlaceInDataFilter(place)
        }
    }

    private fun updateRegions(filter: (AreaInReference) -> Boolean) {
        regions.clear()
        places.filter(filter).forEach { country ->
            country.areas.forEach { region ->
                region.parentId?.let { parentId ->
                    regions.add(
                        Region(
                            id = region.id,
                            name = region.name,
                            parentId = parentId,
                            parentName = country.name
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
                _regionsStateLiveData.postValue(
                    if (regionsList.isEmpty()) RegionState.Empty
                    else RegionState.Content(regionsList)
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
}
