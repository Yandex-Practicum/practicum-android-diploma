package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favourites.viewmodel.FavouritesViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.viewmodel.VacancyViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel { (vacancyId: Long, fromFavoritesScreen: Boolean) ->
        VacancyViewModel(vacancyId, fromFavoritesScreen, get(), get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }
}
