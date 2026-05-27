package ru.practicum.android.diploma.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.favorites.data.database.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.data.dto.FavoriteContactEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteKeySkillEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoritePhoneEntity
import ru.practicum.android.diploma.favorites.data.dto.FavoriteVacancyEntity

@Database(
    version = 1,
    entities = [
        FavoriteVacancyEntity::class,
        FavoriteKeySkillEntity::class,
        FavoriteContactEntity::class,
        FavoritePhoneEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
