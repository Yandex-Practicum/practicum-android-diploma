package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val appModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(get(), get())
    }

    viewModel { FavoritesViewModel(get()) }
    viewModel<VacancyViewModel> {
        VacancyViewModel(get())
    }

}
