package ru.practicum.android.diploma.networkclient.di

import org.koin.dsl.module
import ru.practicum.android.diploma.networkclient.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.networkclient.domain.api.VacanciesRepository

val searchRepositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
