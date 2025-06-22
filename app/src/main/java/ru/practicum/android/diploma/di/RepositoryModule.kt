package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.impl.FavoriteRepositoryImpl
import ru.practicum.android.diploma.data.impl.AreasRepositoryImpl
import ru.practicum.android.diploma.data.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.SearchVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.filters.AreasRepository
import ru.practicum.android.diploma.domain.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.vacancy.api.SearchVacanciesRepository
import ru.practicum.android.diploma.domain.vacancy.api.VacancyDetailsRepository

val repositoryModule = module {
    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    single<SearchVacanciesRepository> {
        SearchVacanciesRepositoryImpl(get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<AreasRepository> {
        AreasRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
}
