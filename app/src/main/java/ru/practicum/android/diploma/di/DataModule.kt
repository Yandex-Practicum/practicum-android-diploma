package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.search.data.network.HHApi
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.sharing.bd.database.AppDatabase

val dataModule = module {

    // --- БАЗА ДАННЫХ ---

    // Создаем единственный экземпляр AppDatabase
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
    single { get<AppDatabase>().favoriteVacancyDao() }



}
