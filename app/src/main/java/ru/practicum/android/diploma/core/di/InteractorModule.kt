package ru.practicum.android.diploma.core.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.detail.impl.DetailInteractorImpl
import ru.practicum.android.diploma.domain.api.SearchInteractor
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl


val interactorModule = module {

    single<DetailInteractor> { DetailInteractorImpl(get()) }
    factory<SearchInteractor> { SearchInteractorImpl(get()) }
}