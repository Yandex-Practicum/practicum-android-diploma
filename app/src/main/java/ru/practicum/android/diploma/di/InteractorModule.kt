package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchVacancyInteractorImpl

val interactorModule = module {

    single<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(get())
    }
}
