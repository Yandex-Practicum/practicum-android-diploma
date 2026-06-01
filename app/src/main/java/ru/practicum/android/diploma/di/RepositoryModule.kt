package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.VacancyDbRepository
import ru.practicum.android.diploma.domain.api.VacancyDetailsRepository

val repositoryModule = module {

    single<VacancyDbRepository> {
        VacancyDbRepositoryImpl(get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
