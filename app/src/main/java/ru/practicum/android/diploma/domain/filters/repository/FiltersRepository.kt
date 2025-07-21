package ru.practicum.android.diploma.domain.filters.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filters.Region
import ru.practicum.android.diploma.domain.models.filters.Country
import ru.practicum.android.diploma.domain.models.filters.Industry
import ru.practicum.android.diploma.util.Resource

interface FiltersRepository {
    fun getCountries(): Flow<Resource<List<Country>>>
    fun getRegions(countryId: String): Flow<Resource<List<Region>>>
    fun getIndustries(): Flow<Resource<List<Industry>>>
}
