package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.favorites.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.impl.FilterInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.vacancies.domain.api.VacanciesInteractor
import ru.practicum.android.diploma.vacancies.domain.impl.VacanciesInteractorImpl

val interactorModule = module {

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }
}
