package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SearchVacancy::class], version = 1)
@TypeConverters(Converters::class)
abstract class SearchVacancyDatabase : RoomDatabase() {
    abstract fun searchVacancyDao() : SearchVacancyDao
}
