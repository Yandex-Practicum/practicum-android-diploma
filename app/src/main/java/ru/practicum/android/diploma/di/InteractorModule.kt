package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.domain.impl.FavoriteInteractorImpl

val interactorModule = module {

    single<FavoriteInteractor> {
        FavoriteInteractorImpl()
    }
}
