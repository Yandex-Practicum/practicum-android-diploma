package ru.practicum.android.diploma.domain.areas.api

import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Places

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Area>>
    suspend fun getAreasByCountry(countryId: String): Resource<Places>
    suspend fun getAllAreas(): Resource<Places>
}
