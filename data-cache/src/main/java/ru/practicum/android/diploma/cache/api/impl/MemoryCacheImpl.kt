package ru.practicum.android.diploma.cache.api.impl

import ru.practicum.android.diploma.cache.api.MemoryCache
import ru.practicum.android.diploma.cache.dto.CountryCache
import java.util.concurrent.ConcurrentHashMap

private const val CACHE_KEY = "cache_key"

class MemoryCacheImpl : MemoryCache {

    private val cache: ConcurrentHashMap<String, List<CountryCache>> = ConcurrentHashMap()

    override fun putCountries(countriesCache: List<CountryCache>) {
        if (getCountries() == null) {
            cache.put(CACHE_KEY, countriesCache)
        }
    }

    override fun getCountries(): List<CountryCache>? {
        return cache.get(CACHE_KEY)
    }

    override fun clearCache() {
        cache.remove(CACHE_KEY)
    }
}
