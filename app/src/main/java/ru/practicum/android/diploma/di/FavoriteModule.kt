package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.db.converters.VacancyEntityConverter
import ru.practicum.android.diploma.domain.api.favorite.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorite.FavoritesRepository
import ru.practicum.android.diploma.domain.impl.favorite.FavoritesInteractorImpl

val favoriteModule = module {
    factory { VacancyEntityConverter() }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    single<FavoritesInteractor> { FavoritesInteractorImpl(get()) }
}
