package ru.practicum.android.diploma.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.ui.model.CountrySelectFragmentScreenState
import ru.practicum.android.diploma.util.network.HttpStatusCode

class CountrySelectViewModel(
    private val areaterInteractor: FilterAreaInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountrySelectFragmentScreenState>()
    fun observeState(): LiveData<CountrySelectFragmentScreenState> = stateLiveData

    init {
        getCountry()
    }

    private fun renderState(state: CountrySelectFragmentScreenState) {
        stateLiveData.postValue(state)
    }

    private fun getCountry() {
        viewModelScope.launch {
            areaterInteractor
                .getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundCountries: List<Area>?, errorMessage: HttpStatusCode?) {
        when {
            errorMessage == HttpStatusCode.NOT_CONNECTED -> {
                renderState(CountrySelectFragmentScreenState.NetworkError)
            }

            foundCountries.isNullOrEmpty() -> {
                renderState(CountrySelectFragmentScreenState.Empty)
            }

            else -> {
                renderState(CountrySelectFragmentScreenState.ChooseItem(convertToCountries(foundCountries)))
            }
        }
    }

    private fun convertToCountries(foundCountries: List<Area>): List<Area> {
        val countries = foundCountries
            .filter { it.parentId == null }
            .sortedBy { if (it.id == "1001") 1 else 0 }

        return countries
    }
}
