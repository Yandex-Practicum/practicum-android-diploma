package ru.practicum.android.diploma.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "vacancy_id")
    val id: Int,
    @ColumnInfo(name = "vacancy_title")
    val title: String,
    val description: String,
    val salary: UUID?,
    val employer: UUID?,
    val area: UUID?
)
