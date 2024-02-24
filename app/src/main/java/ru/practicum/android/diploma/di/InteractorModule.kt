package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.data.GetFavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.impl.GetFavourtiesInteractorImpl

val interactorModule = module {

    single<GetFavouritesInteractor> {
        GetFavourtiesInteractorImpl(get())
    }

}
