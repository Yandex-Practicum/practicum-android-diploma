package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.favourites.data.dao.VacancyDao
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity

@Database(version = 1, entities = [FavoriteEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
