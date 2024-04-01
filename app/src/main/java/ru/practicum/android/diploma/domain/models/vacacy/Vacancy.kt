package ru.practicum.android.diploma.domain.models.vacacy


data class Vacancy(
    val id: String,
    val department: Department?,
    val name: String,
    val area: VacancyArea, // Регион
    val employer: Employer?, // Работодатель
    val salary: Salary?, // Зарплата
    val type: VacancyType, // Тип вакансии
)
