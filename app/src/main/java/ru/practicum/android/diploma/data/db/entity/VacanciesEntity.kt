package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * @param [id] Идентификатор вакансии
 * @param [name] Название вакансии
 * @param [salaryFrom] Зарплата от
 * @param [salaryTo] Зарплата до
 * @param [salaryCurr] Валюта
 * @param [area] Регион ForeignKey
 * @param [employer] Работодатель
 * @param [keySkills] Ключевые навыки
 * @param [employmentForm] Типы занятости
 * @param [professionalRoles] Проффесиональные роли
 * @param [experience] Опыт работы
 */
@Entity(
    tableName = "vacancies",
    foreignKeys = [
        ForeignKey(AreaEntity::class, ["id"], ["area"], onDelete = ForeignKey.CASCADE),
        ForeignKey(EmployerEntity::class, ["id"], ["employer"])
    ]
)
data class VacanciesEntity(
    @PrimaryKey()
    val id: String,
    val name: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurr: String?,
    val area: String,
    val employer: String?,
    val keySkills: List<String>,
    val employmentForm: List<String>,
    val professionalRoles: List<String>,
    val experience: String,
)
