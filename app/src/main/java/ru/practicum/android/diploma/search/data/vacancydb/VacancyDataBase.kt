package ru.practicum.android.diploma.search.data.vacancydb

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.search.data.vacancydb.dao.VacancyDao
import ru.practicum.android.diploma.search.data.vacancydb.entity.VacancyDetailsEntity
import ru.practicum.android.diploma.search.data.vacancydb.entity.VacancyEntity

@Database(entities = [VacancyEntity::class, VacancyDetailsEntity::class], version = 1)
abstract class VacancyDataBase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
