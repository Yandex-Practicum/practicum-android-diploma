package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.presentation.FavouritesViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel

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

    viewModel {
        FavouritesViewModel(get())
    }
}
