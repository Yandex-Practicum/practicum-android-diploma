package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.presentation.favouritevacancies.viewmodel.FavouriteVacanciesViewModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel

val viewModelModule = module {
    // FavouriteVacancies
    viewModel {
        FavouriteVacanciesViewModel(get<FavouriteVacanciesDbInteractor>())
    }
    viewModel {
        VacanciesSearchViewModel(get())
    }
}
