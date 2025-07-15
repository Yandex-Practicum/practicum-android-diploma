package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractorImpl

val domainModule = module {
    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
