package ru.practicum.android.diploma.ui.filter.countryregion.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.models.CountryRegionFilter

class FilterCountryRegionViewModel : ViewModel() {

    private val _countryRegionData = MutableStateFlow(
        CountryRegionFilter(
            countryId = null,
            countryName = null,
            countryVisible = false,
            regionId = null,
            regionName = null,
            regionVisible = false
        )
    )
    val countryRegion: StateFlow<CountryRegionFilter> = _countryRegionData

    fun setCountry(countryId: String?, countryName: String?) {
        _countryRegionData.value = _countryRegionData.value.copy(
            countryId = countryId,
            countryName = countryName,
            countryVisible = true,
        )
    }

    fun setRegion(regionId: String?, regionName: String?) {
        if (!regionId.isNullOrEmpty() && !regionName.isNullOrEmpty()) {
            _countryRegionData.value = _countryRegionData.value.copy(
                regionId = regionId,
                regionName = regionName,
                regionVisible = true,
            )
        } else {
            clearRegion()
        }

    }

    fun clearCountry() {
        if (_countryRegionData.value.regionVisible) {
            _countryRegionData.value = _countryRegionData.value.copy(countryVisible = false)
        } else {
            resetFilter()
        }
    }

    fun clearRegion() {
        if (_countryRegionData.value.countryVisible) {
            _countryRegionData.value = _countryRegionData.value.copy(
                regionId = null,
                regionName = null,
                regionVisible = false,
            )
        } else {
            resetFilter()
        }
    }

    private fun resetFilter() {
        _countryRegionData.value = CountryRegionFilter(
            countryId = null,
            countryName = null,
            countryVisible = false,
            regionId = null,
            regionName = null,
            regionVisible = false
        )
    }
}
