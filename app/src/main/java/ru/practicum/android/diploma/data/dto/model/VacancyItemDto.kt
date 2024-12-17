package ru.practicum.android.diploma.data.dto.model

data class VacancyItemDto(
    val id: String, // Уникальный идентификатор вакансии
    val name: String, // Название вакансии
    val area: AreaDto, // Описание региона, в котором находится вакансия
    val salary: SalaryDto?, // Описание зарплаты
    val employer: EmployerDto, // Информация о компании-работодателе
)
