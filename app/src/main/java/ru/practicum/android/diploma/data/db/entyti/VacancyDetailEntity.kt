package ru.practicum.android.diploma.data.db.entyti

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "vacancy_table")
data class VacancyDetailEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val area: String,
    val vacancyLink: String,
    val contactName: String?,
    val contactEmail: String?,
    val contactPhone: String?,
    val contactComment: String?,
    val employerName: String?,
    val employerUrl: String?,
    val salary: String?,
    val schedule: String?,
    val employment: String?,
    val experience: String?,
    val keySkills: String,
    val description: String,
    val address: String?,
    val isFavorite: Boolean
)

