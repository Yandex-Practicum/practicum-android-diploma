package ru.practicum.android.diploma.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyEntity

@Database(
    entities = [VacancyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
