package ru.practicum.android.diploma.common.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_favorites")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val timeOfLikeSec: Long,
    val companyName: String,
    val salary: String?,
    val area: String,
    val address: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String,
    val keySkill: String,
    val vacancyUrl: String,
    val logoLink: String?
)
