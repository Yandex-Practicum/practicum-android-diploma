package ru.practicum.android.diploma.presentation.filter.selectArea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.presentation.filter.selectArea.state.CountryState

class SelectCountryViewModel(
    val interactor: FilterInteractor,
    val resourceProvider: ResourceProvider
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountryState>()
    fun observeState(): LiveData<CountryState> = stateLiveData

    private val _selectedCountry = MutableLiveData<Country?>()
    val selectedCountry: LiveData<Country?> = _selectedCountry


    private val _selectedArea = MutableLiveData<Area?>()
    val selectedArea: LiveData<Area?> = _selectedArea


    private fun renderState(state: CountryState) {
        stateLiveData.postValue(state)
    }

    fun getCountries() {
        viewModelScope.launch {
            interactor.getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundVacancies: List<Country>?, errorMessage: String?) {
        val countries = mutableListOf<Country>()
        if (foundVacancies != null) {
            countries.addAll(foundVacancies)
        }
        when {
            errorMessage != null -> {
                renderState(
                    CountryState.Error(
                        resourceProvider.getString(R.string.no_connection)
                    )
                )
            }

            else -> {
                renderState(
                    CountryState.Display(
                        countries
                    )
                )
            }
        }
    }

    fun onClicked(country: Country) {
        interactor.setSelectedCountry(country)
    }

    fun loadSelectedCountry() {
        _selectedCountry.value = interactor.getSelectedCountry()
    }

    fun loadSelectedArea() {
        _selectedArea.value = interactor.getSelectedArea()
    }
}