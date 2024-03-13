package ru.practicum.android.diploma.ui.workplace

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared

class WorkplaceViewModel(
    private val filterInfoRepository: FilterInfoRepository
) : ViewModel() {

    private val countryLiveData = MutableLiveData<String>()

    fun observeState(): LiveData<String> = countryLiveData

    private val _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    private val _regionState = MutableLiveData<RegionShared?>()
    val regionState: LiveData<RegionShared?> = _regionState

    init {
        getCountryInfo()
        getRegionInfo()
    }

    fun getCountryInfo() {
        viewModelScope.launch {
            filterInfoRepository.getCountryFlow()
                .collect() { country ->
                    _countryState.postValue(country)
                }
        }
    }

    fun getRegionInfo() {
        viewModelScope.launch {
            filterInfoRepository.getRegionFlow()
                .collect() { region ->
                    _regionState.postValue(region)
                }
        }
    }

    fun setCountryInfo(country: CountryShared?) {
        filterInfoRepository.setCountryFlow(country)
    }

    fun setRegionInfo(region: RegionShared?) {
        filterInfoRepository.setRegionFlow(region)
    }
}
