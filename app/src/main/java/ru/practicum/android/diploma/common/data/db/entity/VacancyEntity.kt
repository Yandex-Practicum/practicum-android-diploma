package ru.practicum.android.diploma.common.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_favorites")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: String?,
    val employer: String?,
    val area: String?,
    val address: String?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val description: String?,
    val keySkills: String?,
    val vacancyUrl: String?,
    val timeOfLikeSec: Long
)
