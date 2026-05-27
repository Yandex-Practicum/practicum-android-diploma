package ru.practicum.android.diploma.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.converter.Converter
import ru.practicum.android.diploma.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.db.entity.FavoriteVacancyEntity

@TypeConverters(Converter::class)
@Database(version = 1, entities = [FavoriteVacancyEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao

    companion object {
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database.db"
        )
    }
}
