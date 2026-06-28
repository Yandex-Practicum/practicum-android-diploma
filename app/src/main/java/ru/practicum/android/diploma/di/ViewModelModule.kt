package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.viewmodels.FavouritesViewModel
import ru.practicum.android.diploma.presentation.viewmodels.FiltersViewModel
import ru.practicum.android.diploma.presentation.viewmodels.IndustryViewModel
import ru.practicum.android.diploma.presentation.viewmodels.SearchViewModel

val ViewModelModule = module {
    viewModel { SearchViewModel(get(), get()) }

    viewModel { FavouritesViewModel(get()) }

    viewModel { FiltersViewModel(get()) }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get(), get())
    }

    viewModel { IndustryViewModel(get()) }
}
