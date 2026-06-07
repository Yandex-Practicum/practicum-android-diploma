package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.interactors.AreasInteractor
import ru.practicum.android.diploma.domain.interactors.DetailsInteractor
import ru.practicum.android.diploma.domain.interactors.FavoritesInteractor
import ru.practicum.android.diploma.domain.interactors.FilterInteractor
import ru.practicum.android.diploma.domain.interactors.VacanciesInteractor
import ru.practicum.android.diploma.domain.interactors.impl.AreasInteractorImpl
import ru.practicum.android.diploma.domain.interactors.impl.DetailsInteractorImpl
import ru.practicum.android.diploma.domain.interactors.impl.FavoritesInteractorImpl
import ru.practicum.android.diploma.domain.interactors.impl.FilterInteractorImpl
import ru.practicum.android.diploma.domain.interactors.impl.VacanciesInteractorImpl

val domainModule = module {

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(repository = get())
    }

    single<DetailsInteractor> {
        DetailsInteractorImpl(repository = get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(repository = get())
    }

    single<FavoritesInteractor> {
        FavoritesInteractorImpl(repository = get())
    }

    single<AreasInteractor> {
        AreasInteractorImpl(repository = get())
    }
}
