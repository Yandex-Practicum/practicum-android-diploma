package ru.practicum.android.diploma.di.modules

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.favorite.domain.FavoritesInteractor
import ru.practicum.android.diploma.favorite.domain.FavoritesInteractorImpl

@Module
interface InteractorModule {
    @Binds
    fun bindFavoritesInteractor(favoritesInteractor: FavoritesInteractorImpl): FavoritesInteractor
}