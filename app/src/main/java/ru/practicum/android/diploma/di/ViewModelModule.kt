package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteScreenViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterScreenViewModel
import ru.practicum.android.diploma.vacancy.presentation.viewmodel.VacancyScreenViewModel

val viewModelModule = module {

    viewModel {
        FavoriteScreenViewModel(get())
    }
    viewModel {
        FilterScreenViewModel(get())
    }

    viewModel {
        FilterScreenViewModel(get())
    }

    viewModel {
        VacancyScreenViewModel(get())
    }

}
