package ru.practicum.android.diploma.ui.filter.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.FilterRepositoryRegionFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared

class WorkplaceViewModel(
    private val filterRepositoryCountryFlow: FilterRepositoryCountryFlow,
    private val filterRepositoryRegionFlow: FilterRepositoryRegionFlow
) : ViewModel() {

    private var _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    private val _regionState = MutableLiveData<RegionShared?>()
    val regionState: LiveData<RegionShared?> = _regionState

    init {
        initSubscribe()
    }

    private fun initSubscribe() {
        with(viewModelScope) {
            launch {
                filterRepositoryCountryFlow.getCountryFlow()
                    .collect { country ->
                        debugLog(TAG) { "filterRepositoryCountryFlow, collect: country = $country" }
                        _countryState.postValue(country)
                    }
            }

            launch {
                filterRepositoryRegionFlow.getRegionFlow()
                    .collect { region ->
                        debugLog(TAG) { "filterRepositoryRegionFlow, collect: region = $region" }
                        _regionState.postValue(region)
                    }
            }
        }
    }

    fun setCountryInfo(country: CountryShared?) {
        filterRepositoryCountryFlow.setCountryFlow(country)
    }

    fun setRegionInfo(region: RegionShared?) {
        filterRepositoryRegionFlow.setRegionFlow(region)
    }

    companion object {
        private const val TAG = "WorkplaceViewModel"
    }
}
