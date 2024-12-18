package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.data.search.impl.VacanciesRepositoryImpl

val repositoryModule = module {

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

}
