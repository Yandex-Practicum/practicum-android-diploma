package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.vacancies.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository

val repositoryModule = module {

    factory<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get())
    }
}
