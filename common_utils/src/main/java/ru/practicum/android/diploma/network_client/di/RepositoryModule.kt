package ru.practicum.android.diploma.network_client.di

import org.koin.dsl.module
import ru.practicum.android.diploma.network_client.data.VacanciesRepositoryImpl
import ru.practicum.android.diploma.network_client.domain.api.VacanciesRepository

val searchRepositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
