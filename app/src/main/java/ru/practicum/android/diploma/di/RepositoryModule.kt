package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository

val repositoryModule = module {
    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(get())
    }
    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), androidContext())
    }
    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
}
