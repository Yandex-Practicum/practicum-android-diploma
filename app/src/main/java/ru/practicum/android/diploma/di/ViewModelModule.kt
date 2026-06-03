package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.viewmodel.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filtration.region.viewmodel.ChooseRegionViewModel
import ru.practicum.android.diploma.presentation.filtration.industry.viewmodel.IndustryViewModel
import ru.practicum.android.diploma.presentation.filtration.viewmodel.FiltrationViewModel
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.viewmodel.VacancyViewModel

val viewModelModule = module {
    viewModelOf(::JobSearchViewModel)
    viewModelOf(::VacancyViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::FiltrationViewModel)
    viewModelOf(::ChooseRegionViewModel)
    viewModelOf(::IndustryViewModel)
}
