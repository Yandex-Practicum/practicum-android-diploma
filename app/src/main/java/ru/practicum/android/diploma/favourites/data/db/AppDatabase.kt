package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.core.data.mapper.VacancyMapper
import ru.practicum.android.diploma.favourites.data.dao.VacancyDao
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity

@Database(version = 2, entities = [FavoriteEntity::class])
@TypeConverters(VacancyMapper::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}
