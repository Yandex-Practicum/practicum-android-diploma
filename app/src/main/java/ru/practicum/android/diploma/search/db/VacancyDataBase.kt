package ru.practicum.android.diploma.search.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VacancyEntity::class, VacancyDetailsEntity::class], version = 1)
abstract class VacancyDataBase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
