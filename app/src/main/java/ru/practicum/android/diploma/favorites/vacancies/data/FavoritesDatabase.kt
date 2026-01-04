package ru.practicum.android.diploma.favorites.vacancies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.favorites.vacancies.data.db.converters.FavoriteVacancyConverters
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity

@Database(
    entities = [FavoriteVacancyEntity::class],
    version = 1,
//    version = 2,
    exportSchema = false
)
@TypeConverters(FavoriteVacancyConverters::class)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
