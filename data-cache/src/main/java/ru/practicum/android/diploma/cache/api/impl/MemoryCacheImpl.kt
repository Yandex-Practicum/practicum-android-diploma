package ru.practicum.android.diploma.cache.api.impl

import android.util.LruCache
import ru.practicum.android.diploma.cache.api.MemoryCache
import ru.practicum.android.diploma.cache.dto.CountryCache

private const val CACHE_KEY = "cache_key"
class MemoryCacheImpl() : MemoryCache {

    private val cache: LruCache<String, List<CountryCache>>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8
        cache = LruCache(cacheSize)
    }

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
