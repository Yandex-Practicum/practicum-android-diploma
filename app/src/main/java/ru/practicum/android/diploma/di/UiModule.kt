package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.features.search.presentation.viewModel.SearchViewModel
import org.koin.dsl.bind

val uiModule = module {
    viewModelOf(::SearchViewModel).bind()
}