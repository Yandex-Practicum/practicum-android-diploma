package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.favourites.data.db.MainDB
import ru.practicum.android.diploma.search.data.network.JobApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient

const val BASE_URL = "https://api.hh.ru/"
const val DB_NAME = "FindYourJob_db"

val dataModule = module {

    single<NetworkClient> {
        RetrofitClient(
            jobApiService = get(),
            context = androidContext()
        )
    }

    single<JobApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = MainDB::class.java,
            name = DB_NAME
        ).build()
    }

}
