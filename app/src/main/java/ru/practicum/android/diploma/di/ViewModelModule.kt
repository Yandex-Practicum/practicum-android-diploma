package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.details.DetailsViewModel
import ru.practicum.android.diploma.ui.favorite.FavoriteViewModel
import ru.practicum.android.diploma.ui.filter.workplace.WorkplaceViewModel
import ru.practicum.android.diploma.ui.filter.workplace.country.CountryViewModel
import ru.practicum.android.diploma.ui.filter.workplace.region.RegionViewModel
import ru.practicum.android.diploma.ui.search.SearchViewModel

val viewModelModule = module {

    viewModel {
        DetailsViewModel(get())
    }

    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        CountryViewModel(get(), get())
    }

    viewModel {
        RegionViewModel(get(), get(), get())
    }

    viewModel {
        FavoriteViewModel()
    }

    viewModel {
        WorkplaceViewModel(get())
    }
}
