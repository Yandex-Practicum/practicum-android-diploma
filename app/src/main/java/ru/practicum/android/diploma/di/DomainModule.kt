package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor
import ru.practicum.android.diploma.domain.search.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.vacancydetails.api.GetVacancyDetailsUseCase
import ru.practicum.android.diploma.domain.vacancydetails.impl.GetVacancyDetailsUseCaseImpl

val domainModule = module {
    factory<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    factory<GetVacancyDetailsUseCase> {
        GetVacancyDetailsUseCaseImpl(get())
    }
}
