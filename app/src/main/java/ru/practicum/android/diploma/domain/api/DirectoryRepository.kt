package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.filter.Area
import ru.practicum.android.diploma.domain.models.filter.Country
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.util.Resource

interface DirectoryRepository {
    fun getIndustries(): Flow<Resource<List<Industry>>>
    fun getCountries(): Flow<Resource<List<Country>>>
    fun getAreas(areaId: String): Flow<Resource<List<Area>>>
}