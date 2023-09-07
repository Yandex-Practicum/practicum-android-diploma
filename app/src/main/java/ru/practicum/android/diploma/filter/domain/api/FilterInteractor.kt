package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.models.SelectedData


interface FilterInteractor {

    suspend fun getCountries(): Flow<NetworkResponse<List<Country>>>
    suspend fun getRegions(query: String): Flow<NetworkResponse<List<Region>>>


    suspend fun getSelectedData(key: String): SelectedData
    suspend fun saveRegion(key: String, region: Region)
    suspend fun saveCountry(key: String, country: Country)
}