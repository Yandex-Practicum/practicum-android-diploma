package ru.practicum.android.diploma.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.HhApi

private const val BASE_URL = "https://api.hh.ru/"

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL API hh.ru
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер для JSON
            .client(get())
            .build()
            .create(HhApi::class.java)
    }
}
