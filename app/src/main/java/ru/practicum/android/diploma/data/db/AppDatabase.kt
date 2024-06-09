package ru.practicum.android.diploma.data.db

import VacancyEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavoritesDao

@Database(version = 1, entities = [VacancyEntity::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}
