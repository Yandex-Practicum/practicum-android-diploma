package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.converters.ListOfDbConverter
import ru.practicum.android.diploma.data.converters.VacancyDtoConverter
import ru.practicum.android.diploma.data.db.converters.VacancyEntityConverter
import ru.practicum.android.diploma.data.db.AppDatabase

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }

    factory { VacancyEntityConverter() }
    factory { VacancyDtoConverter() }
    factory { ListOfDbConverter() }
}
