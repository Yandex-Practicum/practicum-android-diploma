package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.common.data.NetworkClient
import ru.practicum.android.diploma.search.data.network.HhApiService
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.vacancydetails.data.network.DetailsRetrofitNetworkClient

val dataModule = module {
    single<HhApiService> {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApiService::class.java)
    }

    single {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.shared_pref_storage_key),
            Context.MODE_PRIVATE
        )
    }

    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
        DetailsRetrofitNetworkClient(get(), androidContext())
    }
}
