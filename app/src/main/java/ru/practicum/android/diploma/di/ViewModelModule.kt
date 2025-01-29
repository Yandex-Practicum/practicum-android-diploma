package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.*


//import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.ui.search.SearchViewModel

val viewModelModule = module {
    viewModel<SearchViewModel> {
        SearchViewModel(get())
    }
}
