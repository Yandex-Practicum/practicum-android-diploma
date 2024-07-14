package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.data.FavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.filter.data.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.search.data.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.vacancy.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyRepository

val repositoryModule = module {

    single<SearchRepository> {
        SearchRepositoryImpl(networkClient = get())
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(networkClient = get(), favouritesRepository = get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl()
    }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(db = get())
    }
}
