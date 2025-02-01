package ru.practicum.android.diploma.ui.filter.countryregion.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FilterCountryRegionViewModel : ViewModel() {

    private val _countryId = MutableStateFlow<String?>(null)
    val countryId: StateFlow<String?> get() = _countryId

    private val _countryName = MutableStateFlow<String?>(null)
    val countryName: StateFlow<String?> get() = _countryName

    private val _regionId = MutableStateFlow<String?>(null)
    val regionId: StateFlow<String?> get() = _regionId

    private val _regionName = MutableStateFlow<String?>(null)
    val regionName: StateFlow<String?> get() = _regionName

    fun setCountry(countryId: String?, countryName: String?) {
        _countryId.value = countryId
        _countryName.value = countryName
    }

    fun setRegion(regionId: String?, regionName: String?) {
        _regionId.value = regionId
        _regionName.value = regionName
    }

    fun clearCountry() {
        _countryId.value = null
        _countryName.value = null
    }

    fun clearRegion() {
        _regionId.value = null
        _regionName.value = null
    }
}
