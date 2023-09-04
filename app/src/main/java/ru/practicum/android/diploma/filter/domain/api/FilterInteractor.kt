package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.filter.domain.models.Country


interface FilterInteractor {

    fun filter()

    suspend fun getCountries(): Flow<NetworkResponse<List<Country>>>
}