package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [VacancyEntity::class])
abstract class MainDB : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao
}
