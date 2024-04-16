package ru.practicum.android.diploma.ui.filter.workplace.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class CountryViewModel(
    private val countryInteractor: CountryInteractor,
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountryState>()
    fun observeState(): LiveData<CountryState> = stateLiveData

    init {
        loadCountry()
    }

    private fun loadCountry() {
        renderState(CountryState.Loading)
        viewModelScope.launch {
            countryInteractor.searchCountry()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                    debugLog(TAG) { "Список стран: countryName = ${pair.first?.map { it.name }}, " +
                        "countryId = ${pair.first?.map { it.id }}\n" }
                }
        }
    }

    private fun processResult(countriesList: List<Country>?, errorMessage: Int?) {
        when {
            errorMessage != null -> {
                renderState(
                    CountryState.Error(
                        errorMessage = R.string.server_error
                    )
                )
            }

            else -> {
                renderState(
                    CountryState.Content(
                        region = countriesList!!
                    )
                )
            }
        }
    }

    private fun renderState(countryState: CountryState) {
        stateLiveData.postValue(countryState)
    }

    fun setCountryInfo(country: CountryShared) {
        filterRepositoryCountryFlow.setCountryFlow(country)
        // Если выбрали другой город, то регион сбрасываем
        filterRepositoryRegionFlow.setRegionFlow(null)
    }

    companion object {
        const val TAG = "CountryViewModel"
    }
}
