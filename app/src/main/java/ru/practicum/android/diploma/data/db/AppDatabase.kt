package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.FavouritesVacancyDao
import ru.practicum.android.diploma.data.db.entity.FavouritesVacancyEntity

@Database(version = 5, entities = [FavouritesVacancyEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun favouritesVacancyDao(): FavouritesVacancyDao

}
