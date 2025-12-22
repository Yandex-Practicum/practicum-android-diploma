package ru.practicum.android.diploma.vacancy.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.details.presentation.viewmodel.VacancyDetailsViewModel

// пустой коммент
val VacancyDetailViewModule = module {
    viewModel { VacancyDetailsViewModel() }
}
