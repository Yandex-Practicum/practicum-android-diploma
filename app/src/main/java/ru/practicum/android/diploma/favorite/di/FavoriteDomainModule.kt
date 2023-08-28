package ru.practicum.android.diploma.favorite.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.favorite.data.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.favorite.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorite.domain.impl.FavoritesInteractorImpl

@Module
interface FavoriteDomainModule {
    @Binds
    fun bindFavoritesInteractor(favoritesInteractor: FavoritesInteractorImpl): FavoritesInteractor

}