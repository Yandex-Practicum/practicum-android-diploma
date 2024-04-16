package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared

interface CountryRepositoryFlow {

    fun getCountryFlow(): StateFlow<Map<Int, String>>
}
