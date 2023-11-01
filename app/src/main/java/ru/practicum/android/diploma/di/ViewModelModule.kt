package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationCountryViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationRegionViewModel

val viewModelModule = module {
    viewModel {
        FilterViewModel()
    }

    viewModel {
        FilterLocationViewModel()
    }

    viewModel {
        LocationCountryViewModel()
    }

    viewModel {
        LocationRegionViewModel()
    }
}