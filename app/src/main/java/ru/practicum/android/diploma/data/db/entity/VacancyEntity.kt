package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: String,
    val name: String?,
    val locale: String,
    val contacts: String,
    val department: String?,
    val area: String?,
    val salary: String?,
    val type: String?,
    val address: String?,
    val showLogoInSearch: String?,
    val url: String?,
    val alternateUrl: String?,
    val relations: String?,
    val employer: String?,
    val workingDays: String,
    val workingTimeIntervals: String,
    val workingTimeModes: String?,
    val professionalRoles: String?,
    val experience: String?,
    val employment: String?,
    val advResponseUrl: String?,
    val isAdvVacancy: Boolean
)
