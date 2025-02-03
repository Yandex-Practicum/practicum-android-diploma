package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.CountryViewState

class FilterCountriesViewModel(
    private val filterInteractor: FilterInteractor
) : ViewModel() {

    private var selectedCountry: Country? = null
    private val stateLiveData = MutableLiveData<CountryViewState>()
    fun observeState(): LiveData<CountryViewState> = stateLiveData

    fun getCountries(query: String) { // получить список всех стран
        val lowerCaseQuery = query.lowercase()
        viewModelScope.launch {
            filterInteractor
                .getCountries()
                .collect { viewState ->
                    when (viewState) {
                        is CountryViewState.Success -> {
                            val filter = setOf("Россия", "Украина", "Казахстан", "Беларусь") // Захардкоженный фильтр

                            val filteredCountries = viewState.countryList
                                .map { it.copy(name = decodeString(it.name)) } // Декодируем названия
                                .filter { country -> filter.contains(country.name) } // Оставляем только страны из фильтра

                            Log.d("FilteredResponse", "Отфильтрованные страны: $filteredCountries")

                            if (filteredCountries.isNotEmpty()) {
                                renderState(CountryViewState.Success(filteredCountries))
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
    private fun decodeString(text: String): String {
        return String(text.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
    }

    private fun renderState(state: CountryViewState) {
        stateLiveData.postValue(state)
    }
    fun setCountry(country: Country) {
        selectedCountry = country.copy(selected = false)
    }

}
