package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.industries.IndustriesViewModel
import ru.practicum.android.diploma.ui.country.CountryViewModel
import ru.practicum.android.diploma.ui.favorites.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.region.RegionViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel
import ru.practicum.android.diploma.ui.workplace.WorkplaceViewModel

val viewModelModule = module {

    single {
        DetailViewModel(get(), get())
    }

    single {
        FavoriteViewModel(get())
    }
    single {
        WorkplaceViewModel()
    }

    single {
        IndustriesViewModel(get())
    }

    single {
        SearchViewModel(get(), get())
    }

    single {
        CountryViewModel(get())
    }

    single {
        RegionViewModel(get())
    }
}
