package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.models.Filter
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.CountryViewState
import ru.practicum.android.diploma.common.sharedprefs.models.Country as PrefsCountry

class FilterCountriesViewModel(
    private val filterInteractor: FilterInteractor,
    private val sharedPrefsInteractor: SharedPrefsInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountryViewState>()
    fun observeState(): LiveData<CountryViewState> = stateLiveData

    fun onCountryClicked(country: Country) {
        stateLiveData.postValue(CountryViewState.CountrySelected(country))
    }

    fun getCountries() {
        viewModelScope.launch {
            filterInteractor.getCountries().collect { viewState ->
                stateLiveData.postValue(
                    when (viewState) {
                        is CountryViewState.Success ->
                            CountryViewState.Success(viewState.countryList.sortedBy { sortOrder.indexOf(it.name) })
                        else -> viewState
                    }
                )
            }
        }
    }

    fun saveCountry(country: Country) {
        sharedPrefsInteractor.updateFilter(Filter(areaCountry = PrefsCountry(country.id, country.name)))
    }

    companion object {
        private val sortOrder = listOf(
            "Россия", "Украина", "Казахстан", "Азербайджан", "Беларусь",
            "Грузия", "Кыргызстан", "Узбекистан", "Другие регионы"
        )
    }
}
