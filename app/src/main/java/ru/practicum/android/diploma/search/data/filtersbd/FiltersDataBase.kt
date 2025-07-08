package ru.practicum.android.diploma.search.data.filtersbd

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.search.data.filtersbd.dao.FilterDao
import ru.practicum.android.diploma.search.data.filtersbd.entity.FilterEntity

@Database(entities = [FilterEntity::class], version = 1)
abstract class FiltersDataBase : RoomDatabase() {
    abstract fun filterDao(): FilterDao
}
