package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.presintation.FavoriteVacancyViewModel
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyDetailsViewModel

val viewModelModule = module {
    viewModel {
        VacancySearchViewModel(get())
    }

    viewModel {
        FavoriteVacancyViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get())
    }
}
