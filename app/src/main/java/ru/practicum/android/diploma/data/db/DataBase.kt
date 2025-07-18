package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [VacancyEntity::class]
)
abstract class DataBase : RoomDatabase() {
    abstract fun vacanciesDao(): VacanciesDao
}

