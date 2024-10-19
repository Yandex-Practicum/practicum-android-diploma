package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.presentation.FavoriteVacancyViewModel
import ru.practicum.android.diploma.filters.areas.presentation.BaseAreaSelectViewModel
import ru.practicum.android.diploma.filters.areas.presentation.country.CountrySelectViewModel
import ru.practicum.android.diploma.filters.areas.presentation.region.RegionSelectViewModel
import ru.practicum.android.diploma.filters.base.presentation.FilterSettingsViewModel
import ru.practicum.android.diploma.filters.industries.presentation.IndustrySelectViewModel
import ru.practicum.android.diploma.search.presentation.VacancySearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyDetailsViewModel

val viewModelModule = module {
    viewModel {
        VacancySearchViewModel(get(), get())
    }

    viewModel {
        FavoriteVacancyViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get(), get())
    }

    viewModel {
        IndustrySelectViewModel(get(), get())
    }

    viewModel {
        FilterSettingsViewModel(get())
    }

    viewModel {
        CountrySelectViewModel(get(), get())
    }

    viewModel {
        RegionSelectViewModel(get(), get())
    }

    viewModel {
        BaseAreaSelectViewModel(get())
    }
}
