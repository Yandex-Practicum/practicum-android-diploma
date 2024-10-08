package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel

val viewModelModule = module {
    viewModel {
        VacancySearchViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyViewModel(vacancyId, get())
    }
}
