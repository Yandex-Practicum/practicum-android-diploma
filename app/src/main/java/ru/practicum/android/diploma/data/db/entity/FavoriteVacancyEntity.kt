package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.SalaryDto

@Entity(tableName = "favorite_vacancies_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val area: String?,
    val salary: String?,
    val employer: String?,
    val description: String?
)
