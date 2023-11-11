package ru.practicum.android.diploma.presentation.filter.chooseArea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.ResourceProvider
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.FilterInteractor
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.CountryState

class ChooseCountryViewModel(
    val interactor: FilterInteractor,
    val resourceProvider: ResourceProvider
) : ViewModel() {
    //todo замемнить на реальный areaId
    private val areaId = "113"

    private val stateLiveData = MutableLiveData<CountryState>()
    fun observeState(): LiveData<CountryState> = stateLiveData

    private val _selectedCountry = MutableLiveData<Country?>()

    private val selectedCountry: LiveData<Country?> = interactor.getSelectedCountryLiveData()

    fun getSelectedCountry(): LiveData<Country?> = selectedCountry

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




    fun onAreaClicked(area: Country) {
        interactor.setSelectedCountry(area)
    }
}