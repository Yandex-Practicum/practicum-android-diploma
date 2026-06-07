package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.SelectCountryViewModel
import ru.practicum.android.diploma.presentation.filter.SelectRegionViewModel
import ru.practicum.android.diploma.presentation.filter.WorkplaceViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel(
            vacanciesInteractor = get(),
            filterInteractor = get(),
        )
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

    viewModel {
        FilterViewModel(filterInteractor = get())
    }

    viewModel {
        WorkplaceViewModel(filterInteractor = get())
    }

    viewModel {
        SelectCountryViewModel(areasInteractor = get())
    }

    viewModel {
        SelectRegionViewModel(
            areasInteractor = get(),
            savedStateHandle = get()
        )
    }
}
