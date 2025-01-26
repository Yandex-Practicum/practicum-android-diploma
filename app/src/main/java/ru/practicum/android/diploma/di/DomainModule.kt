package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.search.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.vacancydetails.impl.VacancyDetailsInteractorImpl

val domainModule = module {
    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }
}
