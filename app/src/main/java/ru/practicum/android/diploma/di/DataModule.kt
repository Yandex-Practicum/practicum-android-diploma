package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.database.AppDatabase
import ru.practicum.android.diploma.search.data.converters.SearchVacancyNetworkConverter
import ru.practicum.android.diploma.search.data.network.HHApiService
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.network.NetworkClient

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        androidContext().getSharedPreferences("setting_preferences", Context.MODE_PRIVATE)
    }

    single<HHApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext(), get())
    }

    factory<SearchVacancyNetworkConverter> {
        SearchVacancyNetworkConverter(androidContext())
    }
}
