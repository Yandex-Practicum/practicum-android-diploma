package ru.practicum.android.diploma.search.data.dto

data class SearchResponseVacancy(
    val id: String, // Идентификатор вакансии
    val name: String, // Название вакансии
    val area: AreaDto?, // Регион
    val salary: SalaryDto?, // Зарплата
    val description: String, // Описание вакансии
    val employer: EmployerDto? // Logo
)
