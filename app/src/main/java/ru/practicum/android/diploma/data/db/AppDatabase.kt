package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.model.VacancyEntity

@Database(
    version = 2,
    entities = [VacancyEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacanciesDao(): VacancyDao
}
