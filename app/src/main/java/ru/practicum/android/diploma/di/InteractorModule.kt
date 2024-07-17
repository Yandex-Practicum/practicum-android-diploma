package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.impl.FavouritesInteractorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.sharing.domain.SharingInteractor
import ru.practicum.android.diploma.sharing.domain.impl.SharingInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyInteractorImpl

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(vacancyRepository = get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<FavouritesInteractor> {
        FavouritesInteractorImpl(favouritesRepository = get())
    }

    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
}
