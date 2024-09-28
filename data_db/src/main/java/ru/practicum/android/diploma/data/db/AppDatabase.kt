package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavoriteVacancy::class, SearchVacancy::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
    abstract fun searchVacancyDao(): SearchVacancyDao
}
