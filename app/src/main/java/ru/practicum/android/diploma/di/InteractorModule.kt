package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.search.VacancySearchInteractor
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.country.impl.CountryInteractorImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.details.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.domain.impl.search.VacancySearchInteractorImpl
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.region.RegionInteractor
import ru.practicum.android.diploma.domain.region.impl.RegionInteractorImpl

val interactorModule = module {

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get(), get())
    }

    single<CountryInteractor> {
        CountryInteractorImpl(get())
    }

    single<RegionInteractor> {
        RegionInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<VacancySearchInteractor> {
        VacancySearchInteractorImpl(get())
    }
}
