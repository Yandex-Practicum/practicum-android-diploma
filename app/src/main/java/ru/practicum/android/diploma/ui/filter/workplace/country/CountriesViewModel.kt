package ru.practicum.android.diploma.ui.filter.workplace.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.country.CountriesInteractor
import ru.practicum.android.diploma.domain.models.Country

class CountriesViewModel(
    private val countriesInteractor: CountriesInteractor
) : ViewModel() {

    private var _countriesState = MutableLiveData<CountriesState>()
    val countriesState: LiveData<CountriesState> = _countriesState

    fun getCountries() {
        _countriesState.postValue(CountriesState.Loading)
        viewModelScope.launch {
            countriesInteractor.getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second.toString())
                }
        }
    }

    private fun processResult(data: List<Country>?, message: String?) {
        if (data.isNullOrEmpty()) {
            _countriesState.postValue(CountriesState.Error(message = message ?: "Неизвестная ошибка"))
        } else {
            _countriesState.postValue(CountriesState.Content(data))
        }
    }

}
