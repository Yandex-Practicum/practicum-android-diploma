package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.DetailVacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.DetailVacancyRepository
import ru.practicum.android.diploma.search.data.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository

val repositoryModule = module {
    single<DetailVacancyRepository> {
        DetailVacancyRepositoryImpl(networkClient = get())
    }
    single<SearchVacancyRepository> {
        SearchVacancyRepositoryImpl(networkClient = get())
    }
}
