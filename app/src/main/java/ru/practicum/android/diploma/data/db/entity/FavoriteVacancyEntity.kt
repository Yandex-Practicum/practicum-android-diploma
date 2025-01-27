package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val logoUrl: String?,
    val area: String,
    val salary: String?,
    val employerName: String,
    val description: String?,
    val alternateUrl: String,
    val employment: String?,
    val experience: String?
)
