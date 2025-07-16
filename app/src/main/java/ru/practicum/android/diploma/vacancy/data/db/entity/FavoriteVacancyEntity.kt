package ru.practicum.android.diploma.vacancy.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy_table")
data class FavoriteVacancyEntity(
    @PrimaryKey val id: String,
    val name: String,
    val employerName: String?,
    val areaName: String?,
    val descriptionHtml: String?,

    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,

    val experience: String?,
    val employment: String?,
    val schedule: String?,

    val keySkills: String?,

    val logoUrl: String?,
    val url: String?
)
