package ru.practicum.android.diploma.filters.areas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class BaseAreaSelectViewModel(
    private val interactor: SearchRegionsByNameInteractor,
    private val requestBuilderInteractor: RequestBuilderInteractor
) : ViewModel() {
    private val countryAndRegionStateMap = MutableLiveData<Pair<String, String>>()
    val getCountryAndRegionStateMap: LiveData<Pair<String, String>> = countryAndRegionStateMap

    fun updateFields() {
        val area = requestBuilderInteractor.getCashArea()
        val country = area?.parentName
        val region = area?.name
        if (!country.isNullOrBlank() && !region.isNullOrBlank()) {
            countryAndRegionStateMap.value = Pair(country, region)
        } else if (!country.isNullOrBlank()) {
            countryAndRegionStateMap.value = Pair(country, "")
        } else {
            countryAndRegionStateMap.value = Pair("", "")
        }
    }

    fun clearAreaFilter() {
        requestBuilderInteractor.cleanCashArea()
        updateFields()
    }

    fun clearRegionFilter() {
        requestBuilderInteractor.cleanCashRegion()
        updateFields()
    }

    fun saveArea() {
        requestBuilderInteractor.saveArea()
    }
}
