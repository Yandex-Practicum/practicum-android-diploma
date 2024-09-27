package ru.practicum.android.diploma.networkClient.di

import org.koin.dsl.module
import ru.practicum.android.diploma.networkClient.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.networkClient.domain.api.VacanciesRepository

val searchRepositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
