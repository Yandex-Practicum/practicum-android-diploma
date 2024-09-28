package ru.practicum.android.diploma.data.db.di

import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase

val dbDataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().searchVacancyDao() }
    single { get<AppDatabase>().favoriteVacancyDao() }
    single { Gson() }
}
