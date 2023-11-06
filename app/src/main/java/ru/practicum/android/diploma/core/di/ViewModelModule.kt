package ru.practicum.android.diploma.core.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.detail.DetailViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel


val viewModelModule = module {

    viewModel { SearchViewModel() }
    viewModel { DetailViewModel(get()) }
    viewModelOf(::SearchViewModel)



}
