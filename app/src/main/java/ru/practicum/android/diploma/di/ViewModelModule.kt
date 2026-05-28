package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(vacanciesInteractor = get())
    }

    viewModel {
        VacancyDetailsViewModel(
            detailsInteractor = get(),
            favoritesInteractor = get(),
            savedStateHandle = get(),
        )
    }

    viewModel {
        FavoritesViewModel(
            favoritesInteractor = get(),
        )
    }
}
