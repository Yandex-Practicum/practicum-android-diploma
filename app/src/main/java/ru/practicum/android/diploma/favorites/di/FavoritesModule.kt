package ru.practicum.android.diploma.favorites.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.core.data.database.AppDatabase
import ru.practicum.android.diploma.favorites.data.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModel
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModelImpl

val favoritesModule = module {

    single<FavoriteVacancyDao> {
        get<AppDatabase>().favoriteVacancyDao()
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModelImpl(get())
    }
}
