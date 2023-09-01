package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.filters.presentation.viewModel.FiltersViewModel

val uiModule = module {
    viewModel<FiltersViewModel>{
        FiltersViewModel()
    }
}