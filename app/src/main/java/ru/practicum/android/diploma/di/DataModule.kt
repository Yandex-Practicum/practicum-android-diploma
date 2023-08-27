package ru.practicum.android.diploma.di

import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    factory { Gson() }

    //TODO "add name interface Api":
    // single<iTunesSearchAPITO> {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    //TODO "add name interface Api":
    // .create(iTunesSearchAPI::class.java)
    // }
}