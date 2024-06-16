package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.api.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.impl.FavoritesVacancyInteractorImpl

val interactorModule = module {
    single<FavoritesVacancyInteractor> { FavoritesVacancyInteractorImpl(get()) }
}
