package ru.practicum.android.diploma.di

import DetailsConverter
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyConverter
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyDetailsConverter
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyShortMapper
import ru.practicum.android.diploma.data.room.AppDatabase

val DBModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
    factory { VacancyConverter }
    factory { VacancyDetailsConverter }
    factory { DetailsConverter() }
    factory { VacancyShortMapper }
}
