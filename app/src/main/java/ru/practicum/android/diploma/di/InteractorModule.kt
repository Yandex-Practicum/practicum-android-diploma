package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.country.CountriesInteractor
import ru.practicum.android.diploma.domain.api.region.RegionsInteractor
import ru.practicum.android.diploma.domain.impl.country.CountriesInteractorImpl
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoriteVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.filter.FilterSharedPreferencesInteractor
import ru.practicum.android.diploma.domain.filter.impl.FilterSharedPreferencesInteractorImpl
import ru.practicum.android.diploma.domain.impl.region.RegionsInteractorImpl
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.vacancy.impl.VacancyInteractorImpl

val interactorModule = module {

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    factory<FavoriteVacanciesInteractor> {
        FavoriteVacanciesInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    single<CountriesInteractor> {
        CountriesInteractorImpl(get())
    }

    single<RegionsInteractor> {
        RegionsInteractorImpl(get())
    }

    single<FilterSharedPreferencesInteractor> {
        FilterSharedPreferencesInteractorImpl(get())
    }

}
