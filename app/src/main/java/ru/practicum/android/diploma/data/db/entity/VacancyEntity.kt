package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,

    val name: String,
    val company: String?,
    val city: String?,

    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,

    val logo: String?,

    val description: String,
    val areaName: String?,
    val url: String,

    val addressCity: String?,
    val addressStreet: String?,
    val addressBuilding: String?,
    val addressRaw: String?,

    val experienceId: String?,
    val experienceName: String?,

    val scheduleId: String?,
    val scheduleName: String?,

    val employmentId: String?,
    val employmentName: String?,

    val contactName: String?,
    val contactEmail: String?,

    val employerName: String?,
    val employerLogo: String?,

    val skills: String,
    val phones: String,
)
