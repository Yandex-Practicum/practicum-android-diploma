package ru.practicum.android.diploma.favorites.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModel
import ru.practicum.android.diploma.favorites.ui.FavoritesViewModelImpl

val favoritesModule = module {
    viewModel<FavoritesViewModel> {
        FavoritesViewModelImpl()
    }
}
