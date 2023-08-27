package ru.practicum.android.diploma.details.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: Long,
    val iconUri: String = "",
    val title: String = "",
    val company: String = "",
    val salary: String = "",
)


