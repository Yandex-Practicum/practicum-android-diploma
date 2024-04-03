package ru.practicum.android.diploma.domain.filter.impl

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class FilterRepositoryCountryFlowImpl(
    private val sharedPreferences: FiltersLocalStorage
) : FilterRepositoryCountryFlow {

    private val countryFlow: MutableStateFlow<CountryShared?> = MutableStateFlow(sharedPreferences.loadCountryState())

    override fun setCountryFlow(country: CountryShared?) {
        countryFlow.value = country
        sharedPreferences.saveCountryState(country)
    }

    override fun getCountryFlow(): StateFlow<CountryShared?> = countryFlow
}
