package ru.practicum.android.diploma.favourites.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies_table")
data class VacancyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val vacancyId: Int,
    val name: String,
    val company: String,
    val salary: String,
    val area: String,
    val icon: String,
    val employment: String,
    val experience: String,
    val schedule: String,
    val description: String,
    val contact: String,
    val email: String,
    val phones: String,
    val comment: String
)
