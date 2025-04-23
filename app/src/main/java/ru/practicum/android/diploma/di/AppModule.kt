package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoriteViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val appModule = module {
    viewModel { SearchViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { VacancyViewModel(get(), get()) }
}
