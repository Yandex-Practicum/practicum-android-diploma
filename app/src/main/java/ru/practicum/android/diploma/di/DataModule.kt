package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.HhApi
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.hh.ru/"
private const val CONNECT_TIMEOUT = 30L

val dataModule = module {

    single {
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) // Настройка таймаутов
            .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL API hh.ru
            .addConverterFactory(GsonConverterFactory.create()) // Конвертер для JSON
            .client(get())
            .build()
            .create(HhApi::class.java)
    }
}
