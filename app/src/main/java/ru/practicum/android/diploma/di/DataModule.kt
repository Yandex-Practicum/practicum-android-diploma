package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.HhApi

private const val BASE_URL = "https://api.hh.ru/"
const val FILTER_KEY = "key_for_filter"
const val FILTER_PREFERENCES = "filter_preferences"

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL API hh.ru
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер для JSON
            .client(get())
            .build()
            .create(HhApi::class.java)
    }

    single(named(FILTER_PREFERENCES)) {
        androidContext()
            .getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().vacancyDao()
    }

}
