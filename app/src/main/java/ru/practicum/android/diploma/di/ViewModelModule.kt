package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.ui.FavouritesViewModel
import ru.practicum.android.diploma.filter.ui.country.CountryViewModel
import ru.practicum.android.diploma.filter.ui.filter.FilterViewModel
import ru.practicum.android.diploma.filter.ui.location.LocationViewModel
import ru.practicum.android.diploma.filter.ui.region.RegionViewModel
import ru.practicum.android.diploma.filter.ui.sector.SectorViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

val viewModelModule = module {

    viewModel {
        SearchViewModel()
    }

    viewModel {
        FavouritesViewModel()
    }

    viewModel {
        VacancyViewModel()
    }

    viewModel {
        FilterViewModel()
    }

    viewModel {
        LocationViewModel()
    }

    viewModel {
        CountryViewModel()
    }

    viewModel {
        RegionViewModel()
    }

    viewModel {
        SectorViewModel()
    }

}
