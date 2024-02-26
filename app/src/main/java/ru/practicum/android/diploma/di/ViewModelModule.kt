package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel

val viewModelModule = module {
    viewModel {
        VacancyViewModel(
            detailVacancyUseCase = get(),
            makeCallUseCase = get(),
            sendEmailUseCase = get(),
            shareVacancyUseCase = get()
        )
    }
    viewModel {
        SearchViewModel(searchVacancyUseCase = get())
    }
}
