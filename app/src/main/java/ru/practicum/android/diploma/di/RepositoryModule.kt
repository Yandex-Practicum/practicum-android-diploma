package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.SearchRepository
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl

val repositoryModule = module {
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
}