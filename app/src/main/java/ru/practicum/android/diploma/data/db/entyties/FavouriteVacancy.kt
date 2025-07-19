package ru.practicum.android.diploma.data.db.entyties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.salary.Salary
import ru.practicum.android.diploma.domain.models.vacancydetails.EmploymentForm

@Entity(tableName = "favourites_vacancies")
data class FavouriteVacancy(
    @PrimaryKey
    val id: String,
    val name: String,
    val salary: Salary,
    val employer: String?,
    val experience: String?,
    val employmentForm: EmploymentForm?,
    val description: String,
    val workFormat: List<String>?,
    val alternateUrl: String,
    val keySkills: List<String>?,
    val city: String?,
    val logoUrl: String?,
    val timestamp: Long
)
