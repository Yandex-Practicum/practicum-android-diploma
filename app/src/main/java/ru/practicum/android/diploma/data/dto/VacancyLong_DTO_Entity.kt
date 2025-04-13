package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.domain.models.EmploymentForm
import ru.practicum.android.diploma.domain.models.Experience

data class VacancyLong_DTO_Entity(
    val vacany_id: String, //id вакансии
    val logoUrl: String?, //иконка компании
    val name: String, //название вакансии
    val areaName: String, //город
    val employerName: String, //название компании
    val salaryFrom: Int?, //строка вида "от 100000 RUR" или "з/п не указана"
    val salaryTo: Int?, //строка вида "от 100000 RUR" или "з/п не указана"
    val currency: String, //Валюта
    val alternate_url: String, //ссылка на вакансию
    val experience: Experience?, //опыт работы
    val employmentForm: EmploymentForm?, //форма занятости
    val description: String,
    val keySkills: List<String>? //ключевые навыки
)
