package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.DetailVacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.DetailVacancyRepository

val repositoryModule = module {
    single<DetailVacancyRepository> {
        DetailVacancyRepositoryImpl(networkClient = get())
    }
}
