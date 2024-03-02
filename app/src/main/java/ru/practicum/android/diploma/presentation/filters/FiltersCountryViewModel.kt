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
        countries.clear()
        if (foundCountries.code == 200) {
            if (foundCountries.data != null) {
                countries.addAll(foundCountries.data)
                filtersCountriesStateLiveData.postValue(FiltersCountriesState.Content(countries))
                Log.d("Countries", "Получили список стран")
            }
            else {
                filtersCountriesStateLiveData.postValue(FiltersCountriesState.Empty)
                Log.d("Countries", "Пустой список стран")
            }
        }
        else {
            filtersCountriesStateLiveData.postValue(FiltersCountriesState.Error)
            Log.d("Countries", "Ошибка")
        }
    }
}
