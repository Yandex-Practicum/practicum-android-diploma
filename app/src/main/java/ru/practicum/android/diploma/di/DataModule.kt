package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.EXAMPLE_PREFERENCES

val dataModule = module {

    factory { Gson() }

    single {
        androidContext()
            .getSharedPreferences(EXAMPLE_PREFERENCES, Context.MODE_PRIVATE)
    }
}
