package ru.practicum.android.diploma.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.ApiService
import ru.practicum.android.diploma.data.network.Config

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Config.API_EXAMPLE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single { get<Retrofit>().create(ApiService::class.java) }
}
