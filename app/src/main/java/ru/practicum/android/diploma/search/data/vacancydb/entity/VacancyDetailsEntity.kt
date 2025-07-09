package ru.practicum.android.diploma.search.data.vacancydb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Этот класс пока в разработке, поля могут быть добавлены/изменены
@Entity(tableName = "vacancy_details_table")
data class VacancyDetailsEntity(
    @PrimaryKey val id: String,
    val name: String,
    val employerName: String,
    val logoUrls: String,
    val departmentName: String,
    val areaName: String,
    val role: String,
    val description: String,
    val requirement: String,
    val responsibility: String,
    val salaryFrom: Int,
    val salaryTo: Int,
    val currency: String,
    val schedule: String
)
