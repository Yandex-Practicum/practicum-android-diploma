package ru.practicum.android.diploma.data.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.salary.Salary

@Entity(tableName = "favourites_vacancies")
data class FavouriteVacancy(
    @PrimaryKey
    val id: String,
    val nameVacancy: String,
    val alternateUrl: String,
    val employerName: String?,
    val logo: String?,
    val salary: Salary,
    val city: String?,
    val timestamp: Long
)
