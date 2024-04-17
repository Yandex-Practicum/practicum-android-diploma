package ru.practicum.android.diploma.data.vacancies.dto.list

import ru.practicum.android.diploma.data.vacancies.details.dto.ContactsDto
import ru.practicum.android.diploma.data.vacancies.details.dto.EmployerDto
import ru.practicum.android.diploma.data.vacancies.details.dto.SalaryDto
import ru.practicum.android.diploma.data.vacancies.details.dto.VacancyAreaDto
import ru.practicum.android.diploma.data.vacancies.details.dto.VacancyTypeDto

data class VacancyDto(
    val id: String,
    val department: DepartmentDto?,
    val name: String,
    val area: VacancyAreaDto, // Регион
    val employer: EmployerDto, // Работодатель
    val salary: SalaryDto?, // Зарплата
    val type: VacancyTypeDto, // Тип вакансии
    val contacts: ContactsDto? // Контакты
)
