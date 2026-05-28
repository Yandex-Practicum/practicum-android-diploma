package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.db.converter.FavoriteVacancyDbConverter
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository

val repositoryModule = module {

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    factory { FavoriteVacancyDbConverter() }
}
