package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel

val viewModelModule = module {
    viewModel {
        FilterViewModel()
    }

    viewModel {
        FilterLocationViewModel()
    }
}