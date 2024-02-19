package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.commons.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.commons.domain.impl.FavoriteInteractorImpl

val interactorModule = module {

    single<FavoriteInteractor> {
        FavoriteInteractorImpl()
    }
}