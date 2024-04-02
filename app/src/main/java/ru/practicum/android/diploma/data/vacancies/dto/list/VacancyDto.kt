package ru.practicum.android.diploma.data.vacancies.dto.list

data class VacancyDto(
    val id: String,
    val department: DepartmentDto?,
    val name: String,
    val area: VacancyAreaDto, // Регион
    val employer: EmployerDto, // Работодатель
    val salary: SalaryDto?, // Зарплата
    val type: VacancyTypeDto, // Тип вакансии
)
