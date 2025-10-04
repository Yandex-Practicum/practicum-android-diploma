package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.database.AppDatabase
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.impl.FavoritesInteractorImpl

val favoritesModule = module {
    single<VacancyDao> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
            .getVacancyDao()
    }
    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(get(),get())
    }
    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }
}
