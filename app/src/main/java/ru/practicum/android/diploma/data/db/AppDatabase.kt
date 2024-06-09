package ru.practicum.android.diploma.data.db

import VacancyEntity
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.ConverterForListOfDb
import ru.practicum.android.diploma.data.db.dao.FavoritesDao

@Database(
    version = 1,
    entities = [VacancyEntity::class]
)
@TypeConverters(ConverterForListOfDb::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDao
}
