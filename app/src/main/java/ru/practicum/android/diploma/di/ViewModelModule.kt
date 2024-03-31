package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.details.DetailsViewModel

val viewModelModule = module {

    viewModel {
        DetailsViewModel(get())
    }
}
