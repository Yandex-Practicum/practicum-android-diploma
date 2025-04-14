package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

import android.app.Application.MODE_PRIVATE
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.APP_PREFERENCES
import ru.practicum.android.diploma.data.IRetrofitApiClient
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.DB_NAME
import ru.practicum.android.diploma.data.network.IApiService
import ru.practicum.android.diploma.data.network.RetrofitApiClient

val dataModule = module {

    single<IRetrofitApiClient> {
        RetrofitApiClient(get())
    }

    single<IApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IApiService::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}
