package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancy_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val vacancyId: String,
    val name: String,
    val city: String?,
    val area: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val employerName: String?,
    val employerLogoUrl: String?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String,
    val skills: String?,
    val contactEmail: String?,
    val contactName: String?,
    val contactPhoneNumbers: String?,
    val contactComment: String?,
    val timestamp: Long,
    val url: String
)
