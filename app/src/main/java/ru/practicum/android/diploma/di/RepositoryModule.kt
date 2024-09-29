package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesRepository
import ru.practicum.android.diploma.filter.data.impl.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.vacancies.data.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.vacancies.domain.api.VacanciesRepository

val repositoryModule = module {

    single<FavoritesRepository> {
        FavoritesRepositoryImpl()
    }

    single<FilterRepository> {
        FilterRepositoryImpl()
    }

    single<SearchRepository> {
        SearchRepositoryImpl()
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl()
    }
}
