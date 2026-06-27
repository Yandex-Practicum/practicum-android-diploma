package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.ApiKeyInterceptor
import ru.practicum.android.diploma.data.network.PracticumApiService
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.createLoggingInterceptor

private const val BASE_URL = "https://android-diploma.education-services.ru/"

val NetworkModule = module {
    single<PracticumApiService> {
        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(ApiKeyInterceptor())
            addInterceptor(createLoggingInterceptor())
        }.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PracticumApiService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }
}
