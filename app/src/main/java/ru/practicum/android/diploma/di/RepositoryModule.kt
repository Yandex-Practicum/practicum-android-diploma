package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.impl.DetailsVacancyRepositoryImpl
import ru.practicum.android.diploma.data.impl.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.detailsvacancy.DetailsVacancyRepository
import ru.practicum.android.diploma.domain.repositories.FavoritesRepository
import ru.practicum.android.diploma.domain.repositories.SearchVacanciesRepository

val repositoryModule = module {
    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }
    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(get())
    }

    single<DetailsVacancyRepository> {
        DetailsVacancyRepositoryImpl(get())
    }
}
