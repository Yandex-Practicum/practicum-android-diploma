package ru.practicum.android.diploma.favorites.vacansies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.favorites.vacansies.data.db.converters.FavoriteVacancyConverters
import ru.practicum.android.diploma.favorites.vacansies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacansies.data.db.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavoriteVacancyConverters::class)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
