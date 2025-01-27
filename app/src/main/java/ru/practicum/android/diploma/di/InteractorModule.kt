package ru.practicum.android.diploma.di

import org.koin.dsl.module

import ru.practicum.android.diploma.domain.FavouritesInteractor
import ru.practicum.android.diploma.domain.impl.FavouritesInteractorImpl
import ru.practicum.android.diploma.domain.api.VacancyInteractor
import ru.practicum.android.diploma.domain.impl.VacancyInteractorImpl

val interactorModule = module {

    single<FavouritesInteractor> {
        FavouritesInteractorImpl(get())
    }

    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

}
