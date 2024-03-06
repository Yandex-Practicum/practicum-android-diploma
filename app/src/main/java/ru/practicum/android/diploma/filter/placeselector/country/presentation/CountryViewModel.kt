package ru.practicum.android.diploma.filter.placeselector.country.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.Country
import ru.practicum.android.diploma.favourites.presentation.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.filter.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.StringUtils

class CountryViewModel(private val countryUseCase: GetCountriesUseCase) : ViewModel() {
    private val stateLiveData = MutableLiveData<CountryScreenState>()
    private var isClickAllowed = true
    private val countries: ArrayList<Country> = arrayListOf()

    init {
        getCountries()
    }

    fun observeState(): LiveData<CountryScreenState> = stateLiveData

    private fun getCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            countryUseCase.execute().collect {
                when (it) {
                    is Resource.Success -> {
                        val countryList = it.data!!
                        val filteredCountries = countryList.filter { country ->
                            StringUtils.getCountryList(country.name)
                        }
                        countries.addAll(filteredCountries)
                        countries.add(Country("", "Другие регионы"))
                        renderState(CountryScreenState.Content(countries))
                    }

                    is Resource.InternetError -> renderState(CountryScreenState.Error)
                    is Resource.ServerError -> renderState(CountryScreenState.Error)
                }
            }
        }
    }

    private fun renderState(countryScreenState: CountryScreenState) {
        stateLiveData.postValue(countryScreenState)
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch(Dispatchers.IO) {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
}
