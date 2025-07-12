package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.filtersbd.FiltersDataBase
import ru.practicum.android.diploma.search.data.network.HHApi
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.data.vacancydb.VacancyDataBase

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            VacancyDataBase::class.java,
            "vacancy_database.db"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            FiltersDataBase::class.java,
            "filter_database.db"
        ).build()
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.hh.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<HHApi> {
        get<Retrofit>().create(HHApi::class.java)
    }
}
