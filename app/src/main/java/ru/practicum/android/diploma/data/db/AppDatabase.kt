package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, exportSchema = true, entities = [VacancyEntity::class])
@TypeConverters(PhoneListConverter::class, StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
