package ru.practicum.android.diploma.ui.filters

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.ui.industries.IndustriesFragment
import ru.practicum.android.diploma.ui.workplace.WorkplaceFragment

class FilterViewModel(
    val context: Context
): ViewModel() {

    private val _countryState = MutableLiveData<CountryShared>()
    val countryState: LiveData<CountryShared> = _countryState

    private val _regionState = MutableLiveData<RegionShared>()
    val regionState: LiveData<RegionShared> = _regionState

    private val _industriesState = MutableLiveData<IndustriesShared>()
    val industriesState: LiveData<IndustriesShared> = _industriesState

    init {
        initStateSharedPref()
    }
    fun initStateSharedPref() {
        viewModelScope.launch {
            val sharedPrefs = context.getSharedPreferences(WorkplaceFragment.COUNTRY_PREFERENCES, Context.MODE_PRIVATE)
            val sharedPrefsIndustries =
                context.getSharedPreferences(IndustriesFragment.INDUSTRIES_PREFERENCES, Context.MODE_PRIVATE)

            val countryId = sharedPrefs.getString(WorkplaceFragment.COUNTRY_ID, "")
            val countryName = sharedPrefs.getString(WorkplaceFragment.COUNTRY_TEXT, "")
            val regionId = sharedPrefs.getString(WorkplaceFragment.REGION_ID, "")
            val regionName = sharedPrefs.getString(WorkplaceFragment.REGION_TEXT, "")
            val industriesId = sharedPrefsIndustries.getString(IndustriesFragment.INDUSTRIES_ID, "")
            val industriesName = sharedPrefsIndustries.getString(IndustriesFragment.INDUSTRIES_TEXT, "")

            _countryState.postValue(CountryShared(countryId, countryName))
            _regionState.postValue(RegionShared(regionId, regionName))
            _industriesState.postValue(IndustriesShared(industriesId, industriesName))
        }
    }
}
