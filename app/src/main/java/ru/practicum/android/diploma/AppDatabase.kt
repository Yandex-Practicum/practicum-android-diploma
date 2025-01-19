package ru.practicum.android.diploma

import androidx.room.RoomDatabase
import ru.practicum.android.diploma.favorites.data.dao.FavoriteDao
import ru.practicum.android.diploma.filter.data.dao.FilterDao
import ru.practicum.android.diploma.search.data.dao.SearchDao

abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
    abstract fun filterDao(): FilterDao
    abstract fun searchDao(): SearchDao

}
