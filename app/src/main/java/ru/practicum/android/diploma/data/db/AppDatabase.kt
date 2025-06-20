package ru.practicum.android.diploma.data.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import ru.practicum.android.diploma.data.db.dao.VacanciesDao
import ru.practicum.android.diploma.data.db.entity.VacanciesEntity

@Database(
    version = 3,
    entities = [
        VacanciesEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3, spec = DeleteOldColumn::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacanciesDao(): VacanciesDao
}

@DeleteColumn(tableName = "vacancies", columnName = "is_favorite")
class DeleteOldColumn : AutoMigrationSpec
