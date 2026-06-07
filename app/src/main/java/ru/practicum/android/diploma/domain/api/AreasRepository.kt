package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Country>>
    suspend fun getRegions(countryId: String?): Resource<List<Region>>
}
