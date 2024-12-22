package ru.practicum.android.diploma.di

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.network.HeaderInterceptor
import ru.practicum.android.diploma.data.dto.network.HhApi
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.NetworkClient

val dataModule = module {

    single<ConnectivityManager> {
        androidContext().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }

    single<HhApi> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .client(OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }
}
