package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.search.VacanciesRepositoryImpl
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository

const val FILTER_KEY = "key_for_filter"
const val FILTER_PREFERENCES = "filter_preferences"

val dataModule = module {

    single(named(FILTER_PREFERENCES)) {
        androidContext()
            .getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().vacancyDao()
    }

    factory { VacanciesConverter() }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get())
    }
}
