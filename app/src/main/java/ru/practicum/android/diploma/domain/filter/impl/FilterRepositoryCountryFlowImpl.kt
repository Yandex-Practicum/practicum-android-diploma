package ru.practicum.android.diploma.domain.filter.impl

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.filter.storage.impl.FiltersLocalStorage
import ru.practicum.android.diploma.domain.filter.FilterRepositoryCountryFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

class FilterRepositoryCountryFlowImpl(
    private val sharedPreferences: FiltersLocalStorage
) : FilterRepositoryCountryFlow {

    private val countryFlow: MutableStateFlow<CountryShared?> = MutableStateFlow(sharedPreferences.loadCountryState())

    init {
        GlobalScope.launch {
            countryFlow.collect {
                Log.d("StateMyCountry", "Мы получили во флоу $it")
            }
        }
    }

    override fun setCountryFlow(country: CountryShared?) {
        sharedPreferences.saveCountryState(country)
        countryFlow.value = country
    }

    override fun getCountryFlow(): StateFlow<CountryShared?> = countryFlow
}
