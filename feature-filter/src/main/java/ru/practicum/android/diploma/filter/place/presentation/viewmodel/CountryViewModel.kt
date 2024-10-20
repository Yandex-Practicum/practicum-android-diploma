package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState

class CountryViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val places: MutableList<AreaInReference> = ArrayList<AreaInReference>()

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

    fun initDataFromCache() {
        viewModelScope.launch(Dispatchers.IO) {
            regionInteractor.getCountriesCache()?.let { list ->
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
    }

    fun setPlaceInDataFilter(place: Place) {
        viewModelScope.launch {
            regionInteractor.updatePlaceInDataFilterBuffer(place)
        }
    }
}
