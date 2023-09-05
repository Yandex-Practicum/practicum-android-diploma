package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region

interface FilterRepository {

    fun filter()
    suspend fun getCountry(key: String): Country?

    suspend fun saveRegion(key: String, data: Region)

    suspend fun saveCountry(key: String, data: Country)
}