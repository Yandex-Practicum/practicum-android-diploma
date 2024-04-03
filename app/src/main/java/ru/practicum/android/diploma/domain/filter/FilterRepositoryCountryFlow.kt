package ru.practicum.android.diploma.domain.filter

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

interface FilterRepositoryCountryFlow {

    fun setCountryFlow(country: CountryShared?)

    fun getCountryFlow(): StateFlow<CountryShared?>
}
