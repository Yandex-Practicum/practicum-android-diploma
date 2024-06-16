package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.data.network.HeadHunterRetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.impl.FavoritesVacancyInteractorImpl
import ru.practicum.android.diploma.util.BASE_URL
import ru.practicum.android.diploma.util.SHARED_PREFERENCES

val dataModule = module {
    single {
        androidContext().getSharedPreferences(
            SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
    single<HeadHunterApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HeadHunterApi::class.java)
    }
    single<HeadHunterNetworkClient> {
        HeadHunterRetrofitNetworkClient(get())
    }
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "dreamjob.db")
            .build()
    }
    factory { Gson() }

}
