package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.CountryViewState

class FilterCountriesViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountryViewState>()
    fun observeState(): LiveData<CountryViewState> = stateLiveData

    fun getCountries() { // получить список всех стран
        viewModelScope.launch {
            filterInteractor
                .getCountries()
                .collect { viewState ->
                    when (viewState) {
                        is CountryViewState.Success -> {
                            val sortedCountries = viewState.countryList.sortedBy { country ->
                                sortOrder.indexOf(country.name)
                            }
                            if (sortedCountries.isNotEmpty()) {
                                renderState(CountryViewState.Success(sortedCountries))
                            } else {
                                renderState(CountryViewState.NotFoundError)
                            }
                        }
                        else -> {
                            renderState(viewState)
                        }
                    }
                }
        }
    }

    companion object {
        val sortOrder = listOf(
            "Россия", "Украина", "Казахстан", "Азербайджан", "Беларусь",
            "Грузия", "Кыргызстан", "Узбекистан", "Другие регионы"
        )
    }

    private fun renderState(state: CountryViewState) {
        stateLiveData.postValue(state)
    }

}
