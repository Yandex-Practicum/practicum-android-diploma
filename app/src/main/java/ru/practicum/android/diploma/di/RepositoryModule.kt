package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.vacancies.VacanciesSearchRepositoryImpl
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository

val repositoryModule = module {

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<VacanciesSearchRepository> {
        VacanciesSearchRepositoryImpl(get())
    }
}
