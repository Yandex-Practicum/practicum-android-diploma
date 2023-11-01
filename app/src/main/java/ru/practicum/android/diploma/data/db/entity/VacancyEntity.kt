package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: String,
    val locale: String,
    val contacts: String,
    val employer: String,
    val name: String,
    val salary: String
)
