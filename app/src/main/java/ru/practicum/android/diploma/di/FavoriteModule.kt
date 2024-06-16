package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesRepository

val favoriteModule = module {
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
}
