package ru.practicum.android.diploma.filter.place.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.model.Country
import ru.practicum.android.diploma.filter.place.domain.model.Place
import ru.practicum.android.diploma.filter.place.domain.usecase.RegionInteractor
import ru.practicum.android.diploma.filter.place.presentation.viewmodel.state.CountryState

private const val NUMBER_OF_CACHE_READ_ATTEMPTS = 10
private const val DELAY_BETWEEN_CACHE_READS = 100L

internal class CountryViewModel(
    private val regionInteractor: RegionInteractor,
) : ViewModel() {

    private val places: MutableList<AreaInReference> = ArrayList()

    private val _countriesStateLiveData = MutableLiveData<CountryState>()
    fun observeCountriesState(): LiveData<CountryState> = _countriesStateLiveData

    fun initDataFromCache() {
        viewModelScope.launch(Dispatchers.IO) {
            var attempts = 0
            var countriesFetched = false

            _countriesStateLiveData.postValue(CountryState.Loading)

            while (attempts < NUMBER_OF_CACHE_READ_ATTEMPTS && !countriesFetched) {
                regionInteractor.getCountriesCache()?.let { list ->
                    places.addAll(list)
                    val countries = places.map {
                        Country(
                            id = it.id,
                            name = it.name
                        )
                    }
                    _countriesStateLiveData.postValue(CountryState.Content(countries))
                    countriesFetched = true
                }

                attempts++
                delay(DELAY_BETWEEN_CACHE_READS)
            }

            if (!countriesFetched) {
                _countriesStateLiveData.postValue(CountryState.Empty)
            }
        }
    }

    fun setPlaceInDataFilterReserveBuffer(place: Place) {
        viewModelScope.launch {
            regionInteractor.updatePlaceInDataFilterReserveBuffer(place)
        }
    }
}
