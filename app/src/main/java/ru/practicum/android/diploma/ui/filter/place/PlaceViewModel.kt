package ru.practicum.android.diploma.ui.filter.place

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.PlaceState
import ru.practicum.android.diploma.ui.filter.place.models.Region

class PlaceViewModel(
    country: Country?,
    region: Region?
) : ViewModel() {
    private var countryLocal: Country? = null
    private var regionLocal: Region? = null

    init {
        countryLocal = country
        regionLocal = region
    }

    private val stateLiveData = MutableLiveData<PlaceState>()
    fun observeState(): LiveData<PlaceState> = stateLiveData

    fun changeCountry(countryChange: Country?) {
        countryLocal = countryChange
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
}
