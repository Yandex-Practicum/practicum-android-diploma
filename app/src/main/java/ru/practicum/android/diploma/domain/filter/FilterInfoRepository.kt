package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

interface FilterInfoRepository {

    fun setCountryFlow(country: CountryShared?)

    fun getCountryFlow(): StateFlow<CountryShared?>

    fun setRegionFlow(region: RegionShared?)

    fun getRegionFlow(): StateFlow<RegionShared?>

    fun setIndustriesFlow(industries: IndustriesShared?)

    fun getIndustriesFlow(): StateFlow<IndustriesShared?>

    fun setSalaryTextFlow(salary: SalaryTextShared?)

    fun getSalaryTextFlow(): StateFlow<SalaryTextShared?>

    fun setSalaryBooleanFlow(salary: SalaryBooleanShared?)

    fun getSalaryBooleanFlow(): StateFlow<SalaryBooleanShared?>
}
