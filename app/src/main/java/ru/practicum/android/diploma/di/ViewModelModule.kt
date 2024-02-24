package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.presentation.FavouritesViewModel

val viewModelModule = module {
    viewModel<FavouritesViewModel> {
        FavouritesViewModel(get())
    }
}

