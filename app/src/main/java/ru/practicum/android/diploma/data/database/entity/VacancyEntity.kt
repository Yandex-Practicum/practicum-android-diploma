package ru.practicum.android.diploma.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Salary

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id")
    val id: Int,
    @ColumnInfo(name = "vacancy_title")
    val title: String,
    val description: String,
    val salary: String?,
    val employer: String?,
    val area: String?
)
