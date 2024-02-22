package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorites.FavoritesViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModule = module {

    viewModel {
        VacancyViewModel(
            vacancyInteractor = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            favoriteInteractor = get()
        )
    }
}
