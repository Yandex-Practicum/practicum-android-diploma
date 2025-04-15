package ru.practicum.android.diploma.domain.models

data class VacancyShortDmEntity(
    val vacancyId: Int, // id вакансии
    val logoUrl: String?, // иконка компании
    val name: String, // название вакансии
    val areaName: String, // город
    val employerName: String, // название компании (employer.name)
    val salary: String = "Зарплата не указана"
)
