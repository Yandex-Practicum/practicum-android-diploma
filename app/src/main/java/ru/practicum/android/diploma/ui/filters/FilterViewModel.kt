package ru.practicum.android.diploma.ui.filters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FilterViewModel(
    private val filterInfoRepository: FilterInfoRepository
) : ViewModel() {

    private val _countryState = MutableLiveData<CountryShared?>()
    val countryState: LiveData<CountryShared?> = _countryState

    private val _regionState = MutableLiveData<RegionShared?>()
    val regionState: LiveData<RegionShared?> = _regionState

    private val _industriesState = MutableLiveData<IndustriesShared?>()
    val industriesState: LiveData<IndustriesShared?> = _industriesState

    private val _salaryTextState = MutableLiveData<SalaryTextShared?>()
    val salaryTextState: LiveData<SalaryTextShared?> = _salaryTextState

    private val _salaryBooleanState = MutableLiveData<SalaryBooleanShared?>()
    val salaryBooleanState: LiveData<SalaryBooleanShared?> = _salaryBooleanState

    init {
        initFilterInfo()
    }

    fun initFilterInfo() {
        initCountryInfo()
        initRegionInfo()
        initIndustriesInfo()
        initSalaryTextInfo()
        initSalaryBooleanInfo()
    }

    fun initCountryInfo() {
        viewModelScope.launch {
            filterInfoRepository.getCountryFlow()
                .collect() { country ->
                    _countryState.postValue(country)
                }
        }
    }

    fun initRegionInfo() {
        viewModelScope.launch {
            filterInfoRepository.getRegionFlow()
                .collect() { region ->
                    _regionState.postValue(region)
                }
        }
    }

    fun initIndustriesInfo() {
        viewModelScope.launch {
            filterInfoRepository.getIndustriesFlow()
                .collect() { industries ->
                    _industriesState.postValue(industries)
                }
        }
    }

    fun initSalaryTextInfo() {
        viewModelScope.launch {
            filterInfoRepository.getSalaryTextFlow()
                .collect() { salary ->
                    _salaryTextState.postValue(salary)
                }

        }
    }

    fun initSalaryBooleanInfo() {
        viewModelScope.launch {
            filterInfoRepository.getSalaryBooleanFlow()
                .collect() { salary ->
                    _salaryBooleanState.postValue(salary)
                }

        }
    }

    fun setWorkplaceInfo(country: CountryShared?, region: RegionShared?) {
        filterInfoRepository.setCountryFlow(country)
        filterInfoRepository.setRegionFlow(region)
    }

    fun setIndustriesInfo(industries: IndustriesShared?) {
        filterInfoRepository.setIndustriesFlow(industries)
    }

    fun setSalaryTextInfo(salaryText: SalaryTextShared?) {
        filterInfoRepository.setSalaryTextFlow(salaryText)
    }

    fun setSalaryBooleanInfo(salaryBoolean: SalaryBooleanShared?) {
        filterInfoRepository.setSalaryBooleanFlow(salaryBoolean)
    }
}
