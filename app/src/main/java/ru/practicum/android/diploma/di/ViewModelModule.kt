package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.search.viewmodel.VacancyDescriptionViewModel

val viewModelModule = module {

    viewModel { VacancyDescriptionViewModel(get()) }
}
