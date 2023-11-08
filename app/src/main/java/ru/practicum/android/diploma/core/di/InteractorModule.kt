package ru.practicum.android.diploma.core.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.DetailInteractor
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.impl.DetailInteractorImpl
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl


val interactorModule = module {
    factory<SearchInteractor> { SearchInteractorImpl(get()) }
    factory<DetailInteractor> { DetailInteractorImpl(get(), get()) }
}