package ru.practicum.android.diploma.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.config_db.DatabaseConfig.DATABASE_NAME
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyEntity

@Database(entities = [VacancyEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): VacancyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
