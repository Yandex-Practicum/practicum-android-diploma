package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.impl.FavouritesRepositoryImpl
import ru.practicum.android.diploma.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavouritesRepository
import ru.practicum.android.diploma.domain.api.VacancyRepository

val repositoryModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), get(), get())
    }
    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get(), get())
    }
}
