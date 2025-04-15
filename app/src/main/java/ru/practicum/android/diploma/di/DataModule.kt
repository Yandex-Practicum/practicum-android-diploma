package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.network.token.AccessTokenProvider
import ru.practicum.android.diploma.data.network.token.PrefsAccessTokenProvider
import ru.practicum.android.diploma.data.network.token.TokenInterceptor
import ru.practicum.android.diploma.data.storage.AppPrefsService

val dataModule = module {

    single { AppPrefsService(get()) }

    single { PrefsAccessTokenProvider(get()) } bind AccessTokenProvider::class

    single { TokenInterceptor(get()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<TokenInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.api_link))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
