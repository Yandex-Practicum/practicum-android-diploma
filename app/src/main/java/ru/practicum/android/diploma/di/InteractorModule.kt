package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.DetailInteractor
import ru.practicum.android.diploma.domain.country.CountryInteractor
import ru.practicum.android.diploma.domain.country.impl.CountryInteractorImpl
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.DetailInteractorImpl
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.models.main.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.region.RegionInteractor
import ru.practicum.android.diploma.domain.region.impl.RegionInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<DetailInteractor> {
        DetailInteractorImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }

    single<RegionInteractor> {
        RegionInteractorImpl(get())
    }

    single<CountryInteractor> {
        CountryInteractorImpl(get())
    }
}
