package ru.practicum.android.diploma.domain.models

data class VacancyShort_DM_Entity(
    val vacany_id: String,                // id вакансии
    val logoUrl: String?,          // иконка компании (можно взять logo_urls["90"])
    val name: String,             // название вакансии
    val areaName: String,              // город
    val employerName: String,       // название компании (employer.name)
    val salaryFrom: Int?,           // строка вида "от 100000 RUR" или "з/п не указана"
    val salaryTo: Int?,           // строка вида "от 100000 RUR" или "з/п не указана"
    val currency: String,          //Валюта
)
