package ru.practicum.android.diploma.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class VacancyEntity(

    @PrimaryKey val id: String,
    val name: String,

    val employerId: String?,
    val employerLogoUrl90: String?,
    val employerLogoUrl240: String?,
    val employerLogoUrlOriginal: String?,
    val employerName: String?,
    val employerIsTrusted: Boolean?,
    val employerVacanciesUrl: String?,

    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryGross: Boolean?,
    val salaryTo: Int?,

    val city: String?,
    val experience: String?,
    val employment: String?,
    val description: String,

    val contactEmail: String?,
    val contactName: String?,
    val contactPhone: String?,
    val contactComment: String?,

    val link: String,
    val keySkills: String
)

