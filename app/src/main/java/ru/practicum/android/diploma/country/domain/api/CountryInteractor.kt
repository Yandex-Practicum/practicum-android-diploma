package ru.practicum.android.diploma.country.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.core.domain.models.Area

interface CountryInteractor {
    fun getCountries(): Flow<Resource<List<Area>>>
    fun selectCountry(country: Area)
}
