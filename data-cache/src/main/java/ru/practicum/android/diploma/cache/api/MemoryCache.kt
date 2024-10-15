package ru.practicum.android.diploma.cache.api

import ru.practicum.android.diploma.cache.dto.CountryCache

interface MemoryCache {
    fun putCountries(countriesCache: List<CountryCache>)
    fun getCountries(): List<CountryCache>?
    fun clearCache()
}
