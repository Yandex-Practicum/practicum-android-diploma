package ru.practicum.android.diploma.data.db.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.favorites.data.repositoryimpl.db.FavoriteVacancyDatabase
import ru.practicum.android.diploma.data.db.SearchVacancyDatabase

val dbDataModule = module {
    single {
        Room.databaseBuilder(androidContext(), SearchVacancyDatabase::class.java, "searchVacancies.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<SearchVacancyDatabase>().searchVacancyDao() }

    single {
        Room.databaseBuilder(androidContext(), ru.practicum.android.diploma.favorites.data.repositoryimpl.db.FavoriteVacancyDatabase::class.java, "favoriteVacancies.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<ru.practicum.android.diploma.favorites.data.repositoryimpl.db.FavoriteVacancyDatabase>().favoriteVacancyDao() }
}
