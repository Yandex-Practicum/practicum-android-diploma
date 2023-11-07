package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val city: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val salary: String?,
)
