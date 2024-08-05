package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.HHApiService
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.db.AppDatabase
import androidx.room.Room

const val BASE_URL = "https://api.hh.ru/"
val dataModule = module {
    single<NetworkClient> {
        RetrofitNetworkClient(
            hhApiService = get(),
            context = androidContext()
        )
    }

    single<HHApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HHApiService::class.java)
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}
