package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractorImpl

val interactorModule = module {

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }
}
