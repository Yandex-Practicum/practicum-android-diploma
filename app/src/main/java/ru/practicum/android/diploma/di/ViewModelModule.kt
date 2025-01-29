package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.ui.favourites.FavouritesViewModel

val viewModelModule = module {

    viewModel {
        FavouritesViewModel(get())
    }
}
