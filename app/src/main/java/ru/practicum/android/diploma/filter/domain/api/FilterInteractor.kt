package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region


interface FilterInteractor {

    fun filter()

    suspend fun getCountries(): Flow<NetworkResponse<List<Country>>>
    suspend fun getRegions(): Flow<NetworkResponse<List<Region>>>
    suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>>
}