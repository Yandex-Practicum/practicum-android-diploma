package ru.practicum.android.diploma.filter.place.domain.repository

import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference

interface CacheRepository {
    suspend fun putCountries(countries: List<AreaInReference>)
    suspend fun getCountries(): List<AreaInReference>?
    suspend fun clearCache()
}
