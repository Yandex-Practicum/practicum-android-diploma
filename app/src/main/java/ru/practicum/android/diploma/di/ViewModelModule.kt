package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancy.viewmodel.VacancyViewModel

val viewModelModule = module {

    viewModelOf(::SearchViewModel)

    viewModelOf(::VacancyViewModel)

    viewModelOf(::FavoritesViewModel)

}
