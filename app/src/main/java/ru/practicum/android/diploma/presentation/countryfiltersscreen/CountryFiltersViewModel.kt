package ru.practicum.android.diploma.presentation.countryfiltersscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.repository.FiltersInteractor
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.presentation.countryfiltersscreen.uistate.CountryFiltersUiState
import ru.practicum.android.diploma.util.Resource

class CountryFiltersViewModel(
    private val interactor: FiltersInteractor
) : ViewModel() {

    private val _countryState = MutableLiveData<CountryFiltersUiState>()
    val getCountryState: LiveData<CountryFiltersUiState> = _countryState

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            _countryState.postValue(CountryFiltersUiState.Loading)
            interactor.getCountries()
                .collect {
                    statusCountry(it)
                }
        }
    }

    private fun statusCountry(resource: Resource<List<Country>>) {
        _countryState.postValue(
            when (resource) {
                is Resource.Success -> {
                    CountryFiltersUiState.Content(resource.data!!)
                }

                is Resource.Error -> {
                    CountryFiltersUiState.Error
                }
            }
        )
    }
}
