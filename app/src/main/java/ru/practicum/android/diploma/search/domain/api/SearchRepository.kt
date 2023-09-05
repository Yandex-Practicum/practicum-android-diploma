package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.search.domain.models.FetchResult

interface SearchRepository {
    suspend fun search(query: String): Flow<FetchResult>

    suspend fun getCountries(): Flow<NetworkResponse<List<Country>>>
    suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>>
}