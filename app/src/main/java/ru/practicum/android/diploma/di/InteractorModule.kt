package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractor
import ru.practicum.android.diploma.common.sharedprefs.interactor.SharedPrefsInteractorImpl
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractor
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractorImpl
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractorImpl
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.search.domain.interactor.SearchInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyDetailsInteractorImpl

val interactorModule = module {

    factory<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    factory<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    single<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }

    single<SharedPrefsInteractor> {
        SharedPrefsInteractorImpl(get())
    }
}
