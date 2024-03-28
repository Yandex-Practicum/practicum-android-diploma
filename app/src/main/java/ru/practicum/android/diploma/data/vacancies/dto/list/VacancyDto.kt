package ru.practicum.android.diploma.data.vacancies.dto.list

data class VacancyDto(
    val id: String,
    val department: Department?,
    val name: String,
    val area: VacancyArea, // Регион
    val employer: Employer, // Работодатель
    val salary: Salary?, // Зарплата
    val type: VacancyType, // Тип вакансии
)
