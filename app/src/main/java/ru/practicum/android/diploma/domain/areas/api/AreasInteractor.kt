package ru.practicum.android.diploma.domain.areas.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Places

interface AreasInteractor {
    fun getCountries(): Flow<Resource<List<Area>>>
    fun getRegionsWithCountries(countryId: String?): Flow<Resource<Places>>
}
