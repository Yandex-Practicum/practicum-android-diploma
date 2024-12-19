package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String, // Идентификатор вакансии
    val titleOfVacancy: String, // Название вакансии
    val regionName: String, // Название региона, в котором размещена вакансия
    val salary: String?, // Зарплата
    val employerName: String, // Название компании-работодателя
    val employerLogoUrl: String?, // Ссылка на логотип компании-работодателя
    val isFavorite: Boolean = false
)
