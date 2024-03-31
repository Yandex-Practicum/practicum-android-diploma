package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase

val dbModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
