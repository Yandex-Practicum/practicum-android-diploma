package ru.practicum.android.diploma.filters.areas.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filters.areas.domain.api.SearchRegionsByNameInteractor
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor

class AreaSelectViewModel(
    private val interactor: SearchRegionsByNameInteractor,
    private val requestBuilderInteractor: RequestBuilderInteractor
) : ViewModel() {
    private val countryAndRegionStateMap = MutableLiveData<Map<String, String>>()
    val getCountryAndRegionStateMap: LiveData<Map<String, String>> = countryAndRegionStateMap

    fun updateFields() {
        val fields = TODO("Получить поля страна/регион")
        // countryAndRegionStateMap.value = mapOf("country" to fields.country, "region" to fields.region)
    }

    fun clearAreaFilter() {
        TODO("Очистить фильтр Area")
        updateFields()
    }

    fun clearRegionFilter() {
        TODO("Очистить региона и поставить фильтр страны")
        updateFields()
    }
}
