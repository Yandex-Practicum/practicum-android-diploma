package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.impl.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.vacancy.data.impl.GetVacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsRepository

val repositoryModule = module {

    single<SearchVacancyRepository> {
        SearchVacancyRepositoryImpl(get(), get())
    }

    single<GetVacancyDetailsRepository> {
        GetVacancyDetailsRepositoryImpl(get(), get())
    }
}
