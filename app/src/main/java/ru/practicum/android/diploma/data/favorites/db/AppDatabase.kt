package ru.practicum.android.diploma.data.favorites.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.favorites.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.data.favorites.db.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
