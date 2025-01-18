package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractor
import ru.practicum.android.diploma.favorites.domain.interactor.FavoriteInteractorImpl
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.filter.domain.interactor.FilterInteractorImpl

val interactorModule = module {

    single <FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single <FilterInteractor> {
        FilterInteractorImpl(get())
    }
}
