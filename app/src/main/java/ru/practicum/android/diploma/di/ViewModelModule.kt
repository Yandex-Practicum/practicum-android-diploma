package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.favourite.presentation.FavouriteViewModel
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel

val viewModelModule = module {

    viewModelOf(::SearchViewModel)
    viewModel {
        FiltersViewModel()
    }
    viewModel{
        FavouriteViewModel()
    }
}