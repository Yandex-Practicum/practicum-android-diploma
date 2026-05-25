package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.VacancyActionInteractor
import ru.practicum.android.diploma.domain.impl.SearchInteractor
import ru.practicum.android.diploma.domain.impl.VacancyActionInteractorImpl

val interactorModule = module {
    factory {
        SearchInteractor(get())
    }

    single<VacancyActionInteractor> {
        VacancyActionInteractorImpl()
    }
}
