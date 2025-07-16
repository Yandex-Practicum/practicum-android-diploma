package ru.practicum.android.diploma.sharing.bd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.search.data.filtersbd.dao.FilterDao
import ru.practicum.android.diploma.search.data.filtersbd.entity.FilterEntity
import ru.practicum.android.diploma.search.data.vacancydb.dao.VacancyDao
import ru.practicum.android.diploma.search.data.vacancydb.entity.VacancyEntity
import ru.practicum.android.diploma.vacancy.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.vacancy.data.db.dao.VacancyDetailsDao
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.vacancy.data.db.entity.VacancyDetailsEntity

@Database(
    entities = [
        VacancyEntity::class,
        VacancyDetailsEntity::class,
        FilterEntity::class,
        FavoriteVacancyEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
    abstract fun vacancyDetailsDao(): VacancyDetailsDao
    abstract fun filterDao(): FilterDao
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
