package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.filtersbd.FiltersDataBase
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitClient
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

    single<RetrofitClient> {
        RetrofitClient
    }

}
