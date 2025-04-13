package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

const val DB_NAME = "practicum_diploma.db"

@Database(
    version = 1,
    entities = [
        FavVacancyEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favVacanciesDao(): FavVacanciesDao
}
