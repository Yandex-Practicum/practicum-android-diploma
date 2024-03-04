package ru.practicum.android.diploma.filter.industry.di

import org.koin.dsl.module
import ru.practicum.android.diploma.filter.industry.data.IndustryRepositoryImpl
import ru.practicum.android.diploma.filter.industry.domain.api.IndustryRepository

val industryRepositoryModule = module {
    factory<IndustryRepository> {
        IndustryRepositoryImpl(networkClient = get())
    }
}
