package ru.practicum.android.diploma.domain.areas.api

import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Area

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Area>>
    suspend fun getRegions(countryId: String): Resource<List<Area>>
}
