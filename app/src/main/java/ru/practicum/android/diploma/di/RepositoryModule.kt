package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.vacancysearchscreen.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.models.api.VacanciesRepository

val repositoryModule = module {
    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
