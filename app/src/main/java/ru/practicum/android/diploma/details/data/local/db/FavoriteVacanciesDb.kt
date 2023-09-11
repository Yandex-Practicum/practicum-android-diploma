package ru.practicum.android.diploma.details.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.details.data.local.model.VacancyFullInfoEntity

@Database(version = 7, entities = [VacancyFullInfoEntity::class])
abstract class FavoriteVacanciesDb : RoomDatabase() {
    abstract fun getDao(): FavoriteDao
}