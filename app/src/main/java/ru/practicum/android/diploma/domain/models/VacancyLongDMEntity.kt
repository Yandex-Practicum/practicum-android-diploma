package ru.practicum.android.diploma.domain.models

data class VacancyLongDMEntity(
    val vacancyId: String, // id вакансии
    val logoUrl: String?, // иконка компании
    val name: String, // название вакансии
    val areaName: String, // город
    val employerName: String, // название компании
    val currency: String, // Валюта
    val alternateUrl: String, // ссылка на вакансию
    val experience: Experience?, // опыт работы
    val employmentForm: EmploymentForm?, // форма занятости
    val description: String, // описание вакансии
    val keySkills: List<String>? // ключевые навыки
)
