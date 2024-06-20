package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.data.network.HeadHunterRetrofitNetworkClient
import ru.practicum.android.diploma.data.search.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.data.search.VacancyRepository
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.util.BASE_URL
import ru.practicum.android.diploma.util.Debounce
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

    single<Debounce> {
        Debounce(get())
    }

    factory {
        CoroutineScope(Dispatchers.Main)
    }

    factory { Gson() }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), get(), get())
    }

    single<VacancyRepository> {
        VacancyRepository(get(), get())
    }

}
