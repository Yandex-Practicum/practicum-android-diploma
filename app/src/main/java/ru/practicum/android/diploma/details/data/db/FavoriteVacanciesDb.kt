package ru.practicum.android.diploma.details.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.details.data.model.VacancyEntity
@Database(version = 1, entities = [VacancyEntity::class])
abstract class FavoriteVacanciesDb : RoomDatabase() {
    abstract fun getDao(): FavoriteDao
}