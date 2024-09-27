package ru.practicum.android.diploma.networkClient.di

import org.koin.dsl.module
import ru.practicum.android.diploma.networkClient.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.networkClient.domain.impl.VacanciesInteractorImpl

val searchInteractorModule = module {
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}
