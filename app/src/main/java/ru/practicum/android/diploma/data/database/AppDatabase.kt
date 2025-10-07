package ru.practicum.android.diploma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.database.dao.VacancyDao
import ru.practicum.android.diploma.data.database.entity.AreaEntity
import ru.practicum.android.diploma.data.database.entity.EmployerEntity
import ru.practicum.android.diploma.data.database.entity.SalaryEntity
import ru.practicum.android.diploma.data.database.entity.VacancyEntity

@Database(
    version = 1,
    entities = [
        VacancyEntity::class,
        EmployerEntity::class,
        AreaEntity::class,
        SalaryEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVacancyDao(): VacancyDao
}
