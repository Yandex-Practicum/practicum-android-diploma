package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: Long,
    val name: String?,
    val area: String?,
    val employer: String?,
    val salary: String?,
    val experience: String?,
    val employmentForm: String?,
    val employment: String?,
    val schedule: String?,
    val description: String?,
    val keySkills: String?,
    val alternateUrl: String?,
    val address: String,
    val timeStamp: Long = System.currentTimeMillis()
)
