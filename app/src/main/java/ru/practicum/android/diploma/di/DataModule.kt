package ru.practicum.android.diploma.di

import android.content.Context
import android.net.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.dto.network.HhApi
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.NetworkClient

val dataModule = module {

    val hhBaseURL = "https://api.hh.ru/"

    single<HhApi> {
        Retrofit.Builder()
            .baseUrl(hhBaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }

    single<ConnectivityManager> {
        androidContext().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }

    single<NetworkClient> {
        RetrofitNetworkClient(connectivityManager = get(), hhService = get())
    }
}
