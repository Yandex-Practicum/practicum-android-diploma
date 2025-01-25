package ru.practicum.android.diploma.search.domain.model

data class VacancyItems(
    val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salary: Salary? = null, // Зарплатка в наносекунду)
)
