package ru.practicum.android.diploma.search.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModelImpl

val searchModule = module {
    viewModel<SearchViewModel> {
        SearchViewModelImpl()
    }
}
