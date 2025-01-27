package ru.practicum.android.diploma.common.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_entity")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salaryFrom: Int? = null,
    val salaryTo: Int? = null,
    val salaryCurrency: String? = null,
    var timeOfLikeSec: Long // время добавления в избранное (в секундах от начала эпохи UNIX - 1970-01-01 00:00:00 UTC)
)
