package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.repo.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.vacancydetails.data.repo.DetailsRepositoryImpl
import ru.practicum.android.diploma.vacancydetails.domain.api.DetailsRepository

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
    single<DetailsRepository> {
        DetailsRepositoryImpl(get())
    }
}
