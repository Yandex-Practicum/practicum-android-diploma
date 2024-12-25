package ru.practicum.android.diploma.domain.api.country

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface CountriesRepository {
    fun getCountries(): Flow<Resource<List<Country>>>
}
