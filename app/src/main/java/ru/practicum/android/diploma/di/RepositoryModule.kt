package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.data.GetFavouritesRepositoryImpl
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesRepository

val repositoryModule = module {

    single<GetFavouritesRepository> {
        GetFavouritesRepositoryImpl(get())
    }
}
