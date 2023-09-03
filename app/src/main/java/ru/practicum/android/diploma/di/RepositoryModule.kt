package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.converter.VacancyDbConverter
import ru.practicum.android.diploma.db.data.impl.VacancyDbRepositoryImpl
import ru.practicum.android.diploma.db.domain.api.VacancyDbRepository

val repositoryModule = module {
    factory { VacancyDbConverter() }

    single<VacancyDbRepository> {
        VacancyDbRepositoryImpl(appDataBase = get())
    }
}