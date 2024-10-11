package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.presentation.viewmodel.state.Country
import ru.practicum.android.diploma.filter.presentation.viewmodel.state.CountryState
import ru.practicum.android.diploma.filter.presentation.viewmodel.state.RegionState

class RegionsCountriesViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

    private val _regionsStateLiveData = MutableLiveData<RegionState>()
    fun observeRegionsState(): LiveData<RegionState> = _regionsStateLiveData

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()

    init {
        viewModelScope.launch {
            regionInteractor.listAreas().collect { areas ->
                if (areas.first != null) {
                    places.addAll(areas.first!!)
                }
            }
            val countries = places.map { Country(it.id, it.name) }
            _countriesStateLiveData.postValue(CountryState.Content(countries))
        }
    }

    fun getRegions(country: Int) {
        val regions = places.get(country).areas.map { mapOf(it.id to it.name) }
        _regionsStateLiveData.postValue(RegionState.Content(regions))
    }
}
