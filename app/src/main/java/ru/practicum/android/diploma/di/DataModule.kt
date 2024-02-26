package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.app.App
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val dataModule = module {

    single {
        androidApplication() as App
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "get_vacancy_db")
            .build()
    }
}
