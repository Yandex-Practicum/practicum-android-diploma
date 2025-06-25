package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favorite.viewmodel.FavoriteViewModel
import ru.practicum.android.diploma.ui.filter.FilterViewModel
import ru.practicum.android.diploma.ui.filter.industry.IndustryViewModel
import ru.practicum.android.diploma.ui.filter.place.PlaceViewModel
import ru.practicum.android.diploma.ui.filter.place.country.CountryViewModel
import ru.practicum.android.diploma.ui.filter.place.models.Country
import ru.practicum.android.diploma.ui.filter.place.models.Region
import ru.practicum.android.diploma.ui.filter.place.region.RegionViewModel
import ru.practicum.android.diploma.ui.main.MainViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        VacancyViewModel(get(), get(), get())
    }

    viewModel {
        FavoriteViewModel(get())
    }

    viewModel {
        FilterViewModel(get())
    }

    viewModel {
        IndustryViewModel(get())
    }

    viewModel {
        CountryViewModel(get())
    }

    viewModel { (country: Country?) ->
        RegionViewModel(get(), country)
    }

    viewModel { (country: Country?, region: Region?) ->
        PlaceViewModel(country, region)
    }
}
