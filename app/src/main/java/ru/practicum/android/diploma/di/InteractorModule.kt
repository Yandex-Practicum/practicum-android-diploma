package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.impl.AddToFavouritestInteractorImpl
import ru.practicum.android.diploma.favourites.domain.impl.GetFavourtiesInteractorImpl
import ru.practicum.android.diploma.filter.area.domain.usecase.GetAreaFilterUseCase
import ru.practicum.android.diploma.filter.area.domain.usecase.GetAreasByTextUseCase
import ru.practicum.android.diploma.filter.area.domain.usecase.SaveAreaUseCase
import ru.practicum.android.diploma.filter.domain.usecase.ApplyFilterUseCase
import ru.practicum.android.diploma.filter.domain.usecase.DeleteFiltersUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetApplyFilterFlagUseCase
import ru.practicum.android.diploma.filter.domain.usecase.GetFiltersUseCase
import ru.practicum.android.diploma.filter.domain.usecase.SaveFiltersUseCase
import ru.practicum.android.diploma.filter.placeselector.country.domain.usecase.GetCountriesUseCase
import ru.practicum.android.diploma.filter.placeselector.country.domain.usecase.GetCountryFilterUseCase
import ru.practicum.android.diploma.filter.placeselector.country.domain.usecase.SaveCountryFilterUseCase
import ru.practicum.android.diploma.search.domain.usecase.SearchVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.DetailVacancyUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.MakeCallUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.SendEmailUseCase
import ru.practicum.android.diploma.vacancy.domain.usecase.ShareVacancyUseCase

val interactorModule = module {
    factory {
        DetailVacancyUseCase(detailVacancyRepository = get())
    }
    factory {
        MakeCallUseCase(externalNavigator = get())
    }
    factory {
        SendEmailUseCase(externalNavigator = get())
    }

    factory {
        ShareVacancyUseCase(externalNavigator = get())
    }
    factory {
        SearchVacancyUseCase(searchVacancyRepository = get())
    }
    factory<GetFavouritesInteractor> {
        GetFavourtiesInteractorImpl(get())
    }
    factory<AddToFavouritesInteractor> {
        AddToFavouritestInteractorImpl(get())
    }

    factory {
        GetAreasByTextUseCase(areaRepository = get())
    }
    factory {
        GetCountriesUseCase(filterRepository = get())
    }

    factory {
        SaveCountryFilterUseCase(filterRepository = get())
    }

    factory {
        GetCountryFilterUseCase(filterRepository = get())
    }

    factory {
        SaveAreaUseCase(filterRepository = get())
    }

    factory {
        GetAreaFilterUseCase(filterRepository = get())
    }

    factory {
        GetFiltersUseCase(filterRepository = get())
    }

    factory {
        DeleteFiltersUseCase(filterRepository = get())
    }
    factory {
        SaveFiltersUseCase(filterRepository = get())
    }
    factory {
        ApplyFilterUseCase(filterRepository = get())
    }
    factory {
        GetApplyFilterFlagUseCase(filterRepository = get())
    }
}
