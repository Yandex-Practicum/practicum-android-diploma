package ru.practicum.android.diploma.domain.areas.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Area

interface AreasInteractor {
    fun getCountries(): Flow<Resource<List<Area>>>
    fun getRegions(countryId: String): Flow<Resource<List<Area>>>
}
