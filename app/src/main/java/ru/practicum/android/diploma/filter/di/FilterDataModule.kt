package ru.practicum.android.diploma.filter.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.filter.data.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository

@Module
interface FilterDataModule {
    @Binds
    fun bindFilterRepository(filterRepositoryImpl: FilterRepositoryImpl): FilterRepository
}