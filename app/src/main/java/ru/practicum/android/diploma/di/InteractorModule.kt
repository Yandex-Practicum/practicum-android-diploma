package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.FavoriteInteractor
import ru.practicum.android.diploma.domain.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.IndustriesInteractorImpl

val interactorModule = module {
    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    factory<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }
}
