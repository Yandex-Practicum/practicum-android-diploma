package ru.practicum.android.diploma.presentation.filters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.filters.FiltersInteractor
import ru.practicum.android.diploma.domain.models.CountriesList
import ru.practicum.android.diploma.domain.models.Country

class FiltersCountryViewModel(
    private val filterInteractor: FiltersInteractor,
): ViewModel() {
    private var countries = ArrayList<Country>()
    private val filtersCountriesStateLiveData = MutableLiveData<FiltersCountriesState>()
    fun getFiltersCountriesStateLiveData(): LiveData<FiltersCountriesState> = filtersCountriesStateLiveData

    init {
        filtersCountriesStateLiveData.postValue(FiltersCountriesState.Loading)
    }
    fun fillData()  {
        viewModelScope.launch(Dispatchers.IO) {
            filterInteractor.getCountries().collect {country ->
                loadCountries(country)
            }
        }
    }

    private fun loadCountries(foundCountries: Resource<List<Country>>) {
        if (foundCountries.code == 200) {
            if (foundCountries.data != null) {
                var countriesList: ArrayList<Country> = ArrayList()
                for (country in foundCountries.data) {
                    if (CountriesList.containsCountry(country.name)) {
                        countriesList.add(country)
                    }
                }
                countriesList.add(Country("", "Другие страны", ""))
                countries.clear()
                countries.addAll(countriesList)
                filtersCountriesStateLiveData.postValue(FiltersCountriesState.Content(countries))
            }
            else {
                filtersCountriesStateLiveData.postValue(FiltersCountriesState.Empty)
            }
        }
        else {
            filtersCountriesStateLiveData.postValue(FiltersCountriesState.Error)
        }
    }
}
