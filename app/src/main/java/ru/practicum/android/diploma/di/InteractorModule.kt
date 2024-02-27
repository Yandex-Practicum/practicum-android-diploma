package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.main.SearchInteractor
import ru.practicum.android.diploma.domain.main.impl.SearchInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
