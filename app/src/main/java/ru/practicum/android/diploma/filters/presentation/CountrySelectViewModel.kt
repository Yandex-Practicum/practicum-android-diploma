package ru.practicum.android.diploma.filters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.areas.ui.model.AreaSelectScreenState
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.util.network.HttpStatusCode

class CountrySelectViewModel(
    private val areaInteractor: FilterAreaInteractor,
    private val requestBuilderInteractor: RequestBuilderInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<AreaSelectScreenState>()
    fun observeState(): LiveData<AreaSelectScreenState> = stateLiveData

    init {
        getCountry()
    }

    private fun renderState(state: AreaSelectScreenState) {
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
                renderState(AreaSelectScreenState.NetworkError)
            }

            foundCountries.isNullOrEmpty() -> {
                renderState(AreaSelectScreenState.Empty)
            }

            else -> {
                renderState(
                    AreaSelectScreenState.ChooseItem(
                        convertToCountries(foundCountries)
                    )
                )
            }
        }
    }

    fun saveCountry(area: Area) {
        requestBuilderInteractor.setArea(area.id)
    }

    private fun convertToCountries(foundCountries: List<Area>): List<Area> {
        val countries = foundCountries
            .sortedBy { if (it.id == "1001") 1 else 0 }
        return countries
    }
}
