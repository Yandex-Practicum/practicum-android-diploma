package ru.practicum.android.diploma.data.networkclient.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.networkclient.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.data.networkclient.domain.impl.VacanciesInteractorImpl

val searchInteractorModule = module {
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}
