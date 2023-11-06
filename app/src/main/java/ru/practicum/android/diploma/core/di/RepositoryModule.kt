package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.DetailRepositoryImpl
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.VacancyMapper
import ru.practicum.android.diploma.domain.api.DetailRepository
import ru.practicum.android.diploma.domain.api.SearchRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(get(), get(), get()) }
    single<DetailRepository> { DetailRepositoryImpl(get(), get(), get()) }
    single { VacancyMapper() }
}