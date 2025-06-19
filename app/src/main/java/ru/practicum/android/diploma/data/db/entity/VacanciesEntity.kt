package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.converters.DateConverter
import ru.practicum.android.diploma.data.db.converters.StringListConverter
import java.util.Date

/**
 * @param [id] Идентификатор вакансии
 * @param [name] Название вакансии
 * @param [salaryFrom] Зарплата от
 * @param [salaryTo] Зарплата до
 * @param [salaryCurr] Валюта
 * @param [areaName] Регион
 * @param [employerName] Работодатель
 * @param [employerLogoUrl] Лого работодателя
 * @param [keySkills] Ключевые навыки
 * @param [employmentForm] Типы занятости
 * @param [professionalRoles] Профессиональные роли
 * @param [experience] Опыт работы
 * @param [description] Описание вакансии
 */
@Entity(tableName = "vacancies")
@TypeConverters(StringListConverter::class, DateConverter::class)
data class VacanciesEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    @ColumnInfo(name = "salary_from") val salaryFrom: Int?,
    @ColumnInfo(name = "salary_to") val salaryTo: Int?,
    @ColumnInfo(name = "salary_curr") val salaryCurr: String,
    @ColumnInfo(name = "area_name") val areaName: String,
    @ColumnInfo(name = "employer_name") val employerName: String?,
    @ColumnInfo(name = "employer_logo_url") val employerLogoUrl: String?,
    @ColumnInfo(name = "key_skills") val keySkills: List<String>,
    @ColumnInfo(name = "employment_form") val employmentForm: String,
    @ColumnInfo(name = "professional_roles") val professionalRoles: List<String>,
    val schedule: List<String>,
    val experience: String,
    val description: String,
    @ColumnInfo(defaultValue = "") val address: String,
    val dateAdd: Date,
    val url: String?,
    val employment: String?
)
