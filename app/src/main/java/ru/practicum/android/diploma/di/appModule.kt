package ru.practicum.android.diploma.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<Gson> { GsonBuilder().create() }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("practicum_filter_settings", Context.MODE_PRIVATE)
    }
}
