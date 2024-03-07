package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.presentation.FavouritesViewModel
import ru.practicum.android.diploma.filter.area.presentation.AreaViewModel
import ru.practicum.android.diploma.filter.industry.presentation.BranchViewModel
import ru.practicum.android.diploma.filter.placeselector.PlaceSelectorViewModel
import ru.practicum.android.diploma.filter.placeselector.country.presentation.CountryViewModel
import ru.practicum.android.diploma.filter.presentation.FilterViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel
import ru.practicum.android.diploma.vacancy.presentation.VacancyViewModel

val viewModelModule = module {
    viewModel { (id: Long) ->
        VacancyViewModel(
            detailVacancyUseCase = get(),
            makeCallUseCase = get(),
            sendEmailUseCase = get(),
            shareVacancyUseCase = get(),
            addToFavouritesInteractor = get(),
            id = id
        )
    }
    viewModel {
        SearchViewModel(
            searchVacancyUseCase = get(),
            getFiltersUseCase = get(),
            getApplyFilterFlagUseCase = get()
        )
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        CountryViewModel(
            countryUseCase = get(),
            saveCountryFilterUseCase = get()
        )
    }

    viewModel {
        AreaViewModel(
            areaUseCase = get(),
            saveAreaUseCase = get()
        )
    }
    viewModel {
        BranchViewModel(
            getIndustryByTextUseCase = get(),
            saveIndustryUseCase = get()
        )
    }

    viewModel {
        PlaceSelectorViewModel(
            getFiltersUseCase = get(),
            saveAreaUseCase = get(),
            saveCountryFilterUseCase = get()
        )
    }

    viewModel {
        FilterViewModel(
            getFiltersUseCase = get(),
            saveFilterUseCase = get(),
            deleteFiltersUseCase = get(),
            applyFilterUseCase = get()
        )
    }
}
