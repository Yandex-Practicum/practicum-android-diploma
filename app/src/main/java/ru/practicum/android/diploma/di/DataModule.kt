package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.data.NetworkClient
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.favourites.data.db.AppDatabase
import ru.practicum.android.diploma.vacancy.data.ExternalNavigatorImpl
import ru.practicum.android.diploma.vacancy.domain.api.ExternalNavigator

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(context = androidContext())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(context = androidContext(), hhApi = get())
    }
}
