package ru.practicum.android.diploma.filter.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.ui.FilterViewModel
import ru.practicum.android.diploma.filter.ui.FilterViewModelImpl

val filterModule = module {
    viewModel<FilterViewModel> {
        FilterViewModelImpl()
    }
}
