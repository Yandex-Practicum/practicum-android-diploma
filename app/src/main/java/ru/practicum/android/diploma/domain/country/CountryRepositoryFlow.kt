package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.StateFlow

interface CountryRepositoryFlow {

    fun getCountryFlow(): StateFlow<Map<Int, String>>
}
