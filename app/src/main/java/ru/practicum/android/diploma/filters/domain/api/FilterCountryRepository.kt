package ru.practicum.android.diploma.filters.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface FilterCountryRepository {
    fun getAreas(): Flow<Resource<List<Country>>>
}
