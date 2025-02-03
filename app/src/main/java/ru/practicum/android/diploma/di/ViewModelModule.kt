package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.ui.favourites.viewmodel.FavouritesViewModel
import ru.practicum.android.diploma.ui.filter.common.viewmodel.FilterCommonViewModel
import ru.practicum.android.diploma.ui.filter.country.viewmodel.FilterCountryViewModel
import ru.practicum.android.diploma.ui.filter.industry.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.ui.filter.region.viewmodel.FilterRegionViewModel
import ru.practicum.android.diploma.ui.search.viewmodel.SearchViewModel
import ru.practicum.android.diploma.ui.vacancydetails.viewmodel.VacancyViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel { (vacancyId: Long, fromFavoritesScreen: Boolean) ->
        VacancyViewModel(vacancyId, fromFavoritesScreen, get(), get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        FilterIndustryViewModel(get())
    }

    viewModel {
        FilterCountryViewModel(get())
    }

    viewModel {
        FilterRegionViewModel(get())
    }

    viewModel {
        FilterCommonViewModel(get())
    }
}
