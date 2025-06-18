package ru.practicum.android.diploma.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity

@Database(
    version = 2,
    entities = [
        VacanciesEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacanciesDao(): VacanciesDao
}
