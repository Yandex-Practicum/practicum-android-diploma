package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Region
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.RegionState
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.SelectedCountryState

class RegionsCountriesViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

    private val _selectedCountryStateLiveData = MutableLiveData<SelectedCountryState>()
    fun observeSelectedCountryState(): LiveData<SelectedCountryState> = _selectedCountryStateLiveData

    private val _regionsStateLiveData = MutableLiveData<RegionState>()
    fun observeRegionsState(): LiveData<RegionState> = _regionsStateLiveData

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()
    private val regions: MutableList<Region> = ArrayList<Region>()

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

    private fun setRegions() {
        when(val state = _selectedCountryStateLiveData.value) {
            is SelectedCountryState.SelectedCountry -> getRegions(state.country.id)
            is SelectedCountryState.Empty, null -> getRegionsAll()
            is SelectedCountryState.Error -> regions.clear()
        }
    }

    fun getRegionsAll() {
        regions.clear()
        places.map { country ->
            country.areas.map { region ->
                regions.add(
                    Region(
                        id = region.id,
                        name = region.name,
                        parentId = region.parentId
                    )
                )
            }

        }
        _regionsStateLiveData.postValue(RegionState.Content(regions))
    }

    fun getRegions(countryId: String) {
        regions.clear()
        places.filter {countryId == it.id}.map { filterCountry ->
            filterCountry.areas.map { region ->
                regions.add(
                    Region(
                        id = region.id,
                        name = region.name,
                        parentId = region.parentId
                    )
                )
            }

        }
        _regionsStateLiveData.postValue(RegionState.Content(regions))
    }
}
