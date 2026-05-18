package ru.practicum.android.diploma.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.core.data.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyEntity

@Database(
    version = 1,
    entities = [
        FavoriteVacancyEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase()  {
    abstract fun FavoriteVacancyDao() : FavoriteVacancyDao
}
