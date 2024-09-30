package ru.practicum.android.diploma.database.entities

import androidx.room.Entity
import ru.practicum.android.diploma.database.dao.VacancyDetailsDao

@Entity(tableName = VacancyDetailsDao.TABLE_NAME)
data class VacancyDetailsEntity(
    val id: String,
    val name: String,
    val salary: String?,
    val area: String,
    val address: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String,
    val keySkill: String,
)
