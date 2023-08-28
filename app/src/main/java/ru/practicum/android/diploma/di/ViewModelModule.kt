package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel

val viewModelModule = module {

    viewModelOf(::SearchViewModel)
    viewModel {
        FiltersViewModel()
    }



}