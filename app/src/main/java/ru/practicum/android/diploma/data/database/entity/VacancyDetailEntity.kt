package ru.practicum.android.diploma.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyDetailEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id")
    val id: String,
    @ColumnInfo(name = "vacancy_title")
    val title: String,
    val description: String,
    val salary: String,
    val employer: String,
    val industry: String,
    val area: String,
    val experience: String,
    val schedule: String,
    val employment: String
)
