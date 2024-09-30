package ru.practicum.android.diploma.database.entities

import androidx.room.Entity
import ru.practicum.android.diploma.database.dao.VacancyDetailsDao

@Entity(tableName = VacancyDetailsDao.TABLE_NAME)
data class VacancyDetailsEntity(
    val id: String,
)
