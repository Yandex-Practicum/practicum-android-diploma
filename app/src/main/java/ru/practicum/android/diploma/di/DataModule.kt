package ru.practicum.android.diploma.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.network.JobApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient

const val BASE_URL = "https://api.hh.ru/"
const val DB_NAME = "JobSearch_db"

val dataModule = module {

    single<NetworkClient> {
        RetrofitClient()
    }

    single<JobApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JobApiService::class.java)
    }

}
