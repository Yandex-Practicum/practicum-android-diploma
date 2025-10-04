package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.view_models.MainViewModel

val mainModule = module {
    viewModel {
        MainViewModel()
    }
}
