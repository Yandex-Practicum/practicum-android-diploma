package ru.practicum.android.diploma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.database.entity.VacancyDetailEntity

@Database(
    version = 1,
    entities = [
        VacancyDetailEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVacancyDao(): VacancyDao
}
