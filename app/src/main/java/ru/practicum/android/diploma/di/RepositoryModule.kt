package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.impl.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository

val repositoryModule = module {

    single<SearchVacancyRepository> {
        SearchVacancyRepositoryImpl(get(), get())
    }
}
