package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.network.HeadHunterAPI
import ru.practicum.android.diploma.search.data.network.RetrofitInstance

private const val APPLICATION_PREFERENCES = "application_preferences"

val dataModule = module {

    factory { Gson() }

    single {
        androidContext().getSharedPreferences(APPLICATION_PREFERENCES, Context.MODE_PRIVATE)
    }

    single<HeadHunterAPI> {
        RetrofitInstance.headHunterAPI
    }
}
