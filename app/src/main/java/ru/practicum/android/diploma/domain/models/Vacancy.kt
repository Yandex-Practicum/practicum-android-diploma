package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String, // Идентификатор вакансии
    val titleOfVacancy: String, // Название вакансии
    val regionName: String, // Название региона, в котором размещена вакансия
    val salary: String?, // Зарплата
    val employerName: String, // Название компании-работодателя
    val employerLogoUrl: String?, // Ссылка на логотип компании-работодателя
    val employmentType: String?, // Информация о типе занятости
    val experienceType: String?, // Информация о требуемом опыте работы
    val requirements: String?, // Требования к соискателю
    val responsibilities: String?, // Обязанности по вакансии
    val scheduleType: String? // Тип графика работы (удаленная и т. п.)
)
