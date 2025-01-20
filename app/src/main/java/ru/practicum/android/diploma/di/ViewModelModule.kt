package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.search.view_model.SearchViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel()
    }
}
