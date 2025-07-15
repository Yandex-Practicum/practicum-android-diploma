package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.sharing.bd.database.AppDatabase
import ru.practicum.android.diploma.util.InternetConnectionChecker

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    // Предоставляем DAO из AppDatabase
    single { get<AppDatabase>().vacancyDao() }
    single { get<AppDatabase>().vacancyDetailsDao() }
    single { get<AppDatabase>().filterDao() }

    // --- СЕТЬ ---

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get())
    }

    single<InternetConnectionChecker> {
        InternetConnectionChecker(androidApplication())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
}
