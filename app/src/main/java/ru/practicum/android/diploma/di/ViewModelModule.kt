package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel

val viewModelModule = module {
    viewModel {
        VacanciesSearchViewModel(get())
    }

    viewModel {
        VacancyDetailsViewModel(get())
    }
}
