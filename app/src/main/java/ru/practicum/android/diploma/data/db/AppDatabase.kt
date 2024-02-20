package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.VacancyDtoDao
import ru.practicum.android.diploma.data.db.entyti.VacancyDetailDtoEntity

@Database(version = 1, entities = [VacancyDetailDtoEntity::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun vacancyDtoDao(): VacancyDtoDao
}
