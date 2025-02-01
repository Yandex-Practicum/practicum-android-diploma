package ru.practicum.android.diploma.domain.areas.api

import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Areas

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Area>>
    suspend fun getAreasByCountry(countryId: String): Resource<Areas>
    suspend fun getAllAreas(): Resource<Areas>
}
