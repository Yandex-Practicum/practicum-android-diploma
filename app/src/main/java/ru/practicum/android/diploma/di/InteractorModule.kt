package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.vacancysearchscreen.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.models.api.VacanciesInteractor

val interactorModule = module {
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}
