package ru.practicum.android.diploma.filter.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.filter.ui.FilterViewModel
import ru.practicum.android.diploma.filter.ui.FilterViewModelImpl

val filterModule = module {
    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }
    viewModel<FilterViewModel> {
        FilterViewModelImpl(get())
    }
}
