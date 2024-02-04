package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.favourite.FavouriteInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.details.DetailsInteractor
import ru.practicum.android.diploma.domain.api.regions.RegionsInteractor
import ru.practicum.android.diploma.domain.impl.FavouriteInteractorImpl
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.impl.details.DetailsInteractorImpl
import ru.practicum.android.diploma.domain.impl.regions.RegionsInteractorImpl

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<DetailsInteractor> {
        DetailsInteractorImpl(get())
    }

    single<FavouriteInteractor> {
        FavouriteInteractorImpl(get())
    }

    single<RegionsInteractor> {
        RegionsInteractorImpl(get())
    }
}
