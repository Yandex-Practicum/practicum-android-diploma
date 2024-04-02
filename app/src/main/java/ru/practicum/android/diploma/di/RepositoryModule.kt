package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.filter.country.impl.CountryRepositoryImpl
import ru.practicum.android.diploma.data.vacancies.VacanciesSearchRepositoryImpl
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.search.VacanciesSearchRepository
import ru.practicum.android.diploma.domain.country.CountryRepository
import ru.practicum.android.diploma.domain.filter.FilterRepositoryFlow
import ru.practicum.android.diploma.domain.filter.impl.FilterRepositoryFlowImpl

val repositoryModule = module {

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }

    single<VacanciesSearchRepository> {
        VacanciesSearchRepositoryImpl(get())
    }

    single<CountryRepository> {
        CountryRepositoryImpl(get())
    }

    factory<FilterRepositoryFlow> {
        FilterRepositoryFlowImpl(get())
    }
}
