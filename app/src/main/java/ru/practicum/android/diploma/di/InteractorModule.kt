package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.FavouritesInteractor
import ru.practicum.android.diploma.domain.impl.FavoruitesInteractorImpl

val interactorModule = module {

    single<FavouritesInteractor> {
        FavoruitesInteractorImpl(get())
    }

}
