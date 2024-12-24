package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.favorites.FavoriteVacanciesInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoriteVacanciesInteractorImpl
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

}
