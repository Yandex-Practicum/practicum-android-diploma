package ru.practicum.android.diploma.filter.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.di.annotations.PrefsKey
import ru.practicum.android.diploma.filter.data.DataConverter
import ru.practicum.android.diploma.filter.data.FilterRepositoryImpl
import ru.practicum.android.diploma.filter.data.GsonDataConverter
import ru.practicum.android.diploma.filter.data.SharedPrefsStorageImpl
import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.domain.api.FilterRepository

@Module
class FilterDataModule {
    @Provides
    fun provideFilterRepository(filterRepositoryImpl: FilterRepositoryImpl): FilterRepository {
        return filterRepositoryImpl
    }

    @Provides
    fun provideLocalStorage(sharedPreferences: SharedPrefsStorageImpl): LocalStorage {
        return sharedPreferences
    }

    @Provides
    fun provideDataConverter(gsonDataConverter: GsonDataConverter): DataConverter {
        return gsonDataConverter
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideSharedPreferences(@PrefsKey prefsKey: String, context: Context): SharedPreferences {
        return context
            .getSharedPreferences(prefsKey, Context.MODE_PRIVATE)
    }

}