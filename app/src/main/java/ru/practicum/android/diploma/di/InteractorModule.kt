package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.SearchVacanciesInteractor
import ru.practicum.android.diploma.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.impl.SearchVacanciesInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacancyDetailsInteractorImpl

val interactorModule = module {
    factory<SearchVacanciesInteractor> {
        SearchVacanciesInteractorImpl(get())
    }
    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractorImpl(get())
    }
    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
}
