package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.viewmodels.FavouritesViewModel
import ru.practicum.android.diploma.presentation.viewmodels.SearchViewModel

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }

    viewModel { FavouritesViewModel(get()) }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get(),get())
    }
}
