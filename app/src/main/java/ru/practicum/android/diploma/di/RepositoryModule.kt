package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavouritesRepositoryImpl
import ru.practicum.android.diploma.data.db.FavouriteVacancyDbConvertor
import ru.practicum.android.diploma.domain.FavouritesRepository

val repositoryModule = module {

    factory { FavouriteVacancyDbConvertor() }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get(), get())
    }

}
