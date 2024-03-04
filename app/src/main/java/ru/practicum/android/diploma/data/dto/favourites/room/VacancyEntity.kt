package ru.practicum.android.diploma.data.dto.favourites.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val name: String,
    val area: String,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryGross: Boolean?,
    val experience: String?,
    val schedule: String?,
    val contactName: String?,
    val contactEmail: String?,
    val phones: String?,
    val contactComment: String?,
    val logoUrl: String?,
    val logoUrl90: String?,
    val logoUrl240: String?,
    val address: String?,
    val employerUrl: String?,
    val employerName: String?,
    val employment: String?,
    val keySkills: String?,
    val description: String,
    val salary: String
)
