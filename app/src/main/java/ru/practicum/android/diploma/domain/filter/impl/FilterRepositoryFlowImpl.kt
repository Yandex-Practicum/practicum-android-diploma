package ru.practicum.android.diploma.domain.filter.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositoryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FilterRepositoryFlowImpl(private val sharedPreferences: FiltersLocalStorage) : FilterRepositoryFlow {

    private val countryFlow: MutableStateFlow<CountryShared?> = MutableStateFlow(sharedPreferences.loadCountryState())
    private val regionFlow: MutableStateFlow<RegionShared?> = MutableStateFlow(sharedPreferences.loadRegionState())
    private val industriesFlow: MutableStateFlow<IndustriesShared?> =
        MutableStateFlow(sharedPreferences.loadIndustriesState())
    private val salaryTextFlow: MutableStateFlow<SalaryTextShared?> =
        MutableStateFlow(sharedPreferences.loadSalaryTextState())
    private val salaryBooleanFlow: MutableStateFlow<SalaryBooleanShared?> =
        MutableStateFlow(sharedPreferences.loadSalaryBooleanState())

    override fun setCountryFlow(country: CountryShared?) {
        countryFlow.value = country
        sharedPreferences.saveCountryState(country)
    }

    override fun getCountryFlow(): StateFlow<CountryShared?> = countryFlow

    override fun setRegionFlow(region: RegionShared?) {
        regionFlow.value = region
        sharedPreferences.saveRegionState(region)
    }

    override fun getRegionFlow(): StateFlow<RegionShared?> = regionFlow

    override fun setIndustriesFlow(industries: IndustriesShared?) {
        industriesFlow.value = industries
        sharedPreferences.saveIndustriesState(industries)
    }

    override fun getIndustriesFlow(): StateFlow<IndustriesShared?> = industriesFlow
    override fun setSalaryTextFlow(salaryText: SalaryTextShared?) {
        salaryTextFlow.value = salaryText
        sharedPreferences.saveSalaryTextState(salaryText)
    }

    override fun getSalaryTextFlow(): StateFlow<SalaryTextShared?> = salaryTextFlow
    override fun setSalaryBooleanFlow(salaryBoolean: SalaryBooleanShared?) {
        salaryBooleanFlow.value = salaryBoolean
        sharedPreferences.saveSalaryBooleanState(salaryBoolean)
    }

    override fun getSalaryBooleanFlow(): StateFlow<SalaryBooleanShared?> = salaryBooleanFlow
}
