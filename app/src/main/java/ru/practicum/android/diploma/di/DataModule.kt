package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.search.db.VacancyDataBase

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            VacancyDataBase::class.java,
            "vacancy_database.db"
        ).build()
    }

}
