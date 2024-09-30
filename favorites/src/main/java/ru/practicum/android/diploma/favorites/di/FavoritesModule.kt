package ru.practicum.android.diploma.favorites.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.repositoryimpl.db.FavoriteRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.favorites.domain.usecase.FavoriteInteractor
import ru.practicum.android.diploma.favorites.domain.usecase.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteViewModel

val favoritesModule = module {
    // view-model
    viewModel {
        FavoriteViewModel(
            favoriteInteractor = get()
        )
    }

    // domain
    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(
            favoriteRepository = get(),
        )
    }

    // data
    factory<FavoriteRepository> {
        FavoriteRepositoryImpl(
            dataBase = get()
        )
    }
}
