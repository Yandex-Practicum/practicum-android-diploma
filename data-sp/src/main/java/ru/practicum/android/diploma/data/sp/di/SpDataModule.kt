package ru.practicum.android.diploma.data.sp.di

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.data.sp.api.impl.FilterSpImpl

private const val FILTER_PREFERENCES = "filter_preferences"
val spDataModule = module {

    single<FilterSp> {
        FilterSpImpl(
            filterSp = get(),
            gson = get()
        )
    }

    single {
        androidContext()
            .getSharedPreferences(FILTER_PREFERENCES, AppCompatActivity.MODE_PRIVATE)
    }

    single {
        Gson()
    }
}
