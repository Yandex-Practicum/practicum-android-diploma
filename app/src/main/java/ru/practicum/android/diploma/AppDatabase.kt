package ru.practicum.android.diploma

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.favorites.data.VacancyEntityMapper
import ru.practicum.android.diploma.favorites.data.dao.FavoriteDao
import ru.practicum.android.diploma.filter.data.dao.FilterDao
import ru.practicum.android.diploma.search.data.dao.SearchDao

@Database(version = 1, entities = [VacancyEntity::class])
@TypeConverters(VacancyEntityMapper::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun filterDao(): FilterDao
    abstract fun searchDao(): SearchDao

}
