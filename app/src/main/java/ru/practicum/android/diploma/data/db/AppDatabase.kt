package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDtoDao
import ru.practicum.android.diploma.data.db.entyti.VacancyDetailEntity

@Database(version = 3, entities = [VacancyDetailEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDtoDao(): VacancyDtoDao
}
