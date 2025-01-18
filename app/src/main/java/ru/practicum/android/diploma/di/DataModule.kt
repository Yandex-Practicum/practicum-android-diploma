package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.common.data.network.hhApi
import ru.practicum.android.diploma.favorites.data.repository.FavoritesRepositoryImpl
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.filter.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.domain.repository.FilterRepository

val dataModule = module {

    single {
        androidContext().getSharedPreferences(
            "app_shared_prefs",
            Context.MODE_PRIVATE
        )
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    single <hhApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(hhApi::class.java)
    }

    single {
        Gson()
    }

    single <FavoriteRepository> {
        FavoritesRepositoryImpl(get(), get(), get(), get(), androidApplication())
    }

    single <FilterRepository> {
        FilterRepositoryImpl(get(), get(), get())
    }

}
