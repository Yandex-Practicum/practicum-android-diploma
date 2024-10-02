package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary

data class SearchResponseVacancy(
    val id: String, // Идентификатор вакансии
    val name: String, // Название вакансии
    val area: Area, // Регион
    val salary: Salary?, // Зарплата
    val description: String, // Описание вакансии
    val employer: Employer? = null // Logo
)
