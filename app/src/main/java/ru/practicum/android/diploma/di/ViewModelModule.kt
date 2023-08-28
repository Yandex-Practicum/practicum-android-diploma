package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel

val viewModelModule = module {

    viewModel {
        FiltersViewModel()
    }



}