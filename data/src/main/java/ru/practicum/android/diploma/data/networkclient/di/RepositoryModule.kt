package ru.practicum.android.diploma.data.networkclient.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.networkclient.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.networkclient.domain.api.VacanciesRepository

val searchRepositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
