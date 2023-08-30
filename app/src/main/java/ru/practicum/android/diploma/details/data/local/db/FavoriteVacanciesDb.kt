package ru.practicum.android.diploma.details.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.details.data.local.model.VacancyEntity
@Database(version = 2, entities = [VacancyEntity::class])
abstract class FavoriteVacanciesDb : RoomDatabase() {
    abstract fun getDao(): FavoriteDao
}