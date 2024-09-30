package ru.practicum.android.diploma.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.database.dao.VacancyDetailsDao
import ru.practicum.android.diploma.database.entities.VacancyDetailsEntity

@Database(
    version = 1,
    entities = [
        VacancyDetailsEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancy(): VacancyDetailsDao
}
