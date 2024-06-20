package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.VacancyConverter
import ru.practicum.android.diploma.data.repository.FavoritesVacancyRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesVacancyRepository

val repositoryModule = module {
    single<FavoritesVacancyRepository> { FavoritesVacancyRepositoryImpl(get(), get()) }
    factory { VacancyConverter() }
}
