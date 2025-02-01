package ru.practicum.android.diploma.ui.filter.countryregion.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.util.FilterNames

class FilterCountryRegionViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _countryId = MutableStateFlow(sharedPreferences.getString(FilterNames.COUNTRY_ID, ""))
    val countryId: StateFlow<String?> get() = _countryId

    private val _countryName = MutableStateFlow(sharedPreferences.getString(FilterNames.COUNTRY_NAME, ""))
    val countryName: StateFlow<String?> get() = _countryName

    private val _regionId = MutableStateFlow(sharedPreferences.getString(FilterNames.REGION_ID, ""))
    val regionId: StateFlow<String?> get() = _regionId

    private val _regionName = MutableStateFlow(sharedPreferences.getString(FilterNames.REGION_NAME, ""))
    val regionName: StateFlow<String?> get() = _regionName

    fun setCountry(countryId: String?, countryName: String?) {
        _countryId.value = countryId
        _countryName.value = countryName
        saveFilter()
    }

    fun setRegion(regionId: String?, regionName: String?) {
        _regionId.value = regionId
        _regionName.value = regionName
        saveFilter()
    }

    fun clearCountry() {
        _countryId.value = ""
        _countryName.value = ""
        saveFilter()
    }

    fun clearRegion() {
        _regionId.value = ""
        _regionName.value = ""
        saveFilter()
    }

    fun saveFilter() {
        with(sharedPreferences.edit()) {
            putString(FilterNames.COUNTRY_ID, _countryId.value)
            putString(FilterNames.COUNTRY_NAME, _countryName.value)
            putString(FilterNames.REGION_ID, _regionId.value)
            putString(FilterNames.REGION_NAME, _regionName.value)
            apply()
        }
    }
}
