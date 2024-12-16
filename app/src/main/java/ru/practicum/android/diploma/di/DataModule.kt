package ru.practicum.android.diploma.di

import android.content.Context
import android.net.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.dto.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.NetworkClient

val dataModule = module {

    single<ConnectivityManager> {
        androidContext().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }
}
