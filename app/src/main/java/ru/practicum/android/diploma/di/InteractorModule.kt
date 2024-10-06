package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchVacancyInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.GetVacancyDetailsInteractorImpl

val interactorModule = module {

    single<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(get())
    }
    single<GetVacancyDetailsInteractor> {
        GetVacancyDetailsInteractorImpl(get())
    }
}
