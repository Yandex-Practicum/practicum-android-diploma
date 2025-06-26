package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity

@Database(
    version = 1,
    entities = [
        VacanciesEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacanciesDao(): VacanciesDao
}
