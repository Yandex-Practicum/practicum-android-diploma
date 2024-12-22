package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.industries.IndustriesRepository
import ru.practicum.android.diploma.data.dto.industries.impl.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.search.VacanciesRepository
import ru.practicum.android.diploma.data.search.impl.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.data.vacancy.impl.VacancyRepositoryImpl

val repositoryModule = module {

    factory<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(get())
    }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get())
    }
}
