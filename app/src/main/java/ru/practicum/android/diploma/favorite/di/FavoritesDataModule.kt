package ru.practicum.android.diploma.favorite.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.favorite.data.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.api.FavoriteRepository

@Module
interface FavoritesDataModule {
    @Binds
    fun bindFavoriteRepository(favoriteRepositoryImpl: FavoriteRepositoryImpl): FavoriteRepository
}