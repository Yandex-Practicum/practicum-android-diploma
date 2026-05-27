package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.api.VacancyActionInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailInteractor
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyActionInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<VacancyDetailInteractor> {
        VacancyDetailInteractorImpl(get())
    }

    single<VacancyActionInteractor> {
        VacancyActionInteractorImpl()
    }
}
