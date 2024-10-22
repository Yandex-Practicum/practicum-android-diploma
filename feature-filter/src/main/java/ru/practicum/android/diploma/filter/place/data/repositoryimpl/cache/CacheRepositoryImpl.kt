package ru.practicum.android.diploma.filter.place.data.repositoryimpl.cache

import ru.practicum.android.diploma.cache.api.MemoryCache
import ru.practicum.android.diploma.filter.place.data.mappers.CacheMapper
import ru.practicum.android.diploma.filter.place.domain.model.AreaInReference
import ru.practicum.android.diploma.filter.place.domain.repository.CacheRepository

internal class CacheRepositoryImpl(
    val memoryCache: MemoryCache
) : CacheRepository {
    override suspend fun putCountries(countries: List<AreaInReference>) {
        memoryCache.putCountries(countries.map { CacheMapper.map(it) })
    }

    override suspend fun getCountries(): List<AreaInReference>? {
        return memoryCache.getCountries()?.map { CacheMapper.map(it) }
    }

    override suspend fun clearCache() {
        memoryCache.clearCache()
    }
}
