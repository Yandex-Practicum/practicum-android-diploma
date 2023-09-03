package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.domain.api.VacancyDbInteractor
import ru.practicum.android.diploma.db.domain.impl.VacancyDbInteractorImpl

val interactorModule = module {
    single<VacancyDbInteractor> {
        VacancyDbInteractorImpl(
            vacancyDbConverter = get(),
            vacancyDbRepository = get()
        )
    }
}