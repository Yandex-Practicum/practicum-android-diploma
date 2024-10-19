package ru.practicum.android.diploma.filters.areas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor

class BaseAreaSelectViewModel(
    private val areaCashInteractor: AreaCashInteractor
) : ViewModel() {
    private val countryAndRegionStateMap = MutableLiveData<Pair<String, String>>()
    val getCountryAndRegionStateMap: LiveData<Pair<String, String>> = countryAndRegionStateMap
    init {
        areaCashInteractor.resetCashArea()
    }
    fun updateFields() {
        val area = areaCashInteractor.getCashArea()
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
        areaCashInteractor.cleanCashArea()
        updateFields()
    }

    fun clearRegionFilter() {
        areaCashInteractor.cleanCashRegion()
        updateFields()
    }

    fun resetCashArea() {
        areaCashInteractor.resetCashArea()
    }
    fun saveArea() {
        areaCashInteractor.saveArea()
    }
}
