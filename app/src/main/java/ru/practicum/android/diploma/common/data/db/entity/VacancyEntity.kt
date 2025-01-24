package ru.practicum.android.diploma.common.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.search.domain.model.Salary

@Entity(tableName = "vacancy_entity")
data class VacancyEntity(
    @PrimaryKey
    /*val id: String,
    val job: String,
    val location: String,
    val salary: Int*/
    val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salary: String? = null,//Salary? = null, // Зарплатка в наносекунду)
    var timeOfLikeSec: Long // время добавления в избранное (в секундах от начала эпохи UNIX - 1970-01-01 00:00:00 UTC)
)
