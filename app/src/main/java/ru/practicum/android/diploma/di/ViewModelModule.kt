package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.presentation.viewmodel.FavoriteScreenViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterScreenViewModel
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyDetailsViewModel

val viewModelModule = module {

    viewModel {
        FavoriteScreenViewModel(get(), androidContext())
    }
    viewModel {
        FilterScreenViewModel(get())
    }

    viewModel {
        FilterScreenViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get(), get(), get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

}
