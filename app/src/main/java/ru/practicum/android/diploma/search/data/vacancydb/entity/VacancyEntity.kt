package ru.practicum.android.diploma.search.data.vacancydb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Этот класс пока в разработке, поля могут быть добавлены/изменены
@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val employerName: String,
    val logoUrls: String,
    val areaName: String,
)
