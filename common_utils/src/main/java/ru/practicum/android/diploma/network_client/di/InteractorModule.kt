package ru.practicum.android.diploma.network_client.di

import org.koin.dsl.module
import ru.practicum.android.diploma.network_client.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.network_client.domain.impl.VacanciesInteractorImpl

val searchInteractorModule = module {
    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}
