package ru.practicum.android.diploma.filters.areas.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.util.network.HttpStatusCode

class CountrySelectViewModel(
    private val areaInteractor: FilterAreaInteractor,
    private val areaCashInteractor: AreaCashInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<CountrySelectScreenState>()
    fun observeState(): LiveData<CountrySelectScreenState> = stateLiveData

    init {
        getCountry()
    }

    private fun renderState(state: CountrySelectScreenState) {
        stateLiveData.postValue(state)
    }

    private fun getCountry() {
        viewModelScope.launch {
            areaInteractor
                .getCountries()
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(foundCountries: List<Area>?, errorMessage: HttpStatusCode?) {
        when {
            errorMessage == HttpStatusCode.NOT_CONNECTED -> {
                renderState(CountrySelectScreenState.NetworkError)
            }

            foundCountries.isNullOrEmpty() -> {
                renderState(CountrySelectScreenState.Empty)
            }

            else -> {
                renderState(
                    CountrySelectScreenState.ChooseItem(
                        convertToCountries(foundCountries)
                    )
                )
            }
        }
    }

    fun saveCountry(area: Area) {
        val savedArea = Area("", "", area.id, area.name, emptyList())
        areaCashInteractor.setCashArea(savedArea)
    }

    private fun convertToCountries(foundCountries: List<Area>): List<Area> {
        val countries = foundCountries
            .sortedBy { if (it.id == "1001") 1 else 0 }
        return countries
    }
}
