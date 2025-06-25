package ru.practicum.android.diploma.ui.filter.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.PlaceState
import ru.practicum.android.diploma.ui.filter.place.models.Region

class PlaceViewModel(
    private val country: Country?,
    private val region: Region?
) : ViewModel() {
    private var countryLocal: Country? = null
    private var regionLocal: Region? = null

    private val stateLiveData = MutableLiveData<PlaceState>()
    fun observeState(): LiveData<PlaceState> = stateLiveData

    init {
        countryLocal = country
        regionLocal = region

        stateLiveData.postValue(PlaceState.Content(country, region))
    }

    fun changeCountry(countryChange: Country?) {
        countryLocal = countryChange
        regionLocal = null
        stateLiveData.postValue(PlaceState.Content(countryLocal, regionLocal))
    }

    fun changeRegion(regionChange: Region?) {
        regionLocal = regionChange
        stateLiveData.postValue(PlaceState.Content(countryLocal, regionLocal))
    }

    fun responseRegion() {
        stateLiveData.postValue(PlaceState.ResponseRegion(countryLocal))
    }

    fun saveChanged() {
        stateLiveData.postValue(PlaceState.Save(countryLocal, regionLocal))
    }

    fun clearLiveData() {
        stateLiveData.postValue(PlaceState.Loading)
    }
}
