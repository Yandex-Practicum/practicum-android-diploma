package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.presintation.FavoriteVacancyViewModel
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel

val viewModelModule = module {
    viewModel {
        VacancySearchViewModel(get())
    }

    viewModel {
        FavoriteVacancyViewModel(get())
    }
}
