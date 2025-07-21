package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favouritevacancies.usecases.FavouriteVacanciesDbInteractor
import ru.practicum.android.diploma.presentation.SearchFiltersViewModel
import ru.practicum.android.diploma.presentation.countryfiltersscreen.CountryFiltersViewModel
import ru.practicum.android.diploma.presentation.favouritevacancies.viewmodel.FavouriteVacanciesViewModel
import ru.practicum.android.diploma.presentation.regionsfilterscreen.RegionFilterViewModel
import ru.practicum.android.diploma.presentation.searchfilters.industries.IndustriesFilterViewModel
import ru.practicum.android.diploma.presentation.vacancydetailsscreen.viewmodel.VacancyDetailsViewModel
import ru.practicum.android.diploma.presentation.vacancysearchscreen.viewmodels.VacanciesSearchViewModel
import ru.practicum.android.diploma.presentation.workplacescreen.WorkplaceFiltersViewModel

val viewModelModule = module {
    // FavouriteVacancies
    viewModel {
        FavouriteVacanciesViewModel(get<FavouriteVacanciesDbInteractor>())
    }
    viewModel {
        VacanciesSearchViewModel(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyDetailsViewModel(vacancyId, get(), get(), get())
    }

    viewModel {
        CountryFiltersViewModel(get())
    }

    viewModel {
        WorkplaceFiltersViewModel(get())
    }

    viewModel {
        IndustriesFilterViewModel(get(), get())
    }

    viewModel {
        SearchFiltersViewModel(get())
    }

    viewModel {
        RegionFilterViewModel(get(), get())
    }
}
