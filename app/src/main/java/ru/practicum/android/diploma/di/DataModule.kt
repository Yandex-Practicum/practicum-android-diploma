package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import androidx.room.Room
import org.koin.dsl.module
import ru.practicum.android.diploma.database.AppDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.network.HHApiService
import ru.practicum.android.diploma.util.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient

val dataModule = module {

    single<HHApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru//")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApiService::class.java)
    }

    single<NetworkClient> { RetrofitNetworkClient(androidContext(), get()) }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}
