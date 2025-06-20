package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorite.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.main.MainViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }

    viewModel {
        VacancyViewModel(get(), get(), get())
    }

    viewModel {
        FavoriteViewModel(get())
    }
}
