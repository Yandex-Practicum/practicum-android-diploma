package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.converter.VacancyConverter
import ru.practicum.android.diploma.data.favorites.FavoriteVacanciesRepository
import ru.practicum.android.diploma.data.favorites.impl.FavoriteVacanciesRepositoryImpl
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.industries.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.data.search.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.data.vacancy.impl.VacancyRepositoryImpl

val repositoryModule = module {

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<VacancyConverter> {
        VacancyConverter()
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
    factory<FavoriteVacanciesRepository> {
        FavoriteVacanciesRepositoryImpl(get(), get())
    }

}
