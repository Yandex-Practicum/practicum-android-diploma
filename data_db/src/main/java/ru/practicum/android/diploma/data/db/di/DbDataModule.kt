package ru.practicum.android.diploma.data.db.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.AppDatabase

val dbDataModule = module {

    single {
        AppDatabase.getDatabase(context = androidContext())
    }
}
