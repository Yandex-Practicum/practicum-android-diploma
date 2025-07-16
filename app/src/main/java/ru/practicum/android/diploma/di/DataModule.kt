package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
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
