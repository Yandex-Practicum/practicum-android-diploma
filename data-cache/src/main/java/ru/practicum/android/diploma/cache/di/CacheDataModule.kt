package ru.practicum.android.diploma.cache.di

import org.koin.dsl.module
import ru.practicum.android.diploma.cache.api.MemoryCache
import ru.practicum.android.diploma.cache.api.impl.MemoryCacheImpl

val cacheDataModule = module {
    single<MemoryCache> {
        MemoryCacheImpl()
    }
}
