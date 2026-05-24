package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.interactors.VacanciesInteractor
import ru.practicum.android.diploma.domain.interactors.impl.VacanciesInteractorImpl

val domainModule = module {

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(repository = get())
    }
}
