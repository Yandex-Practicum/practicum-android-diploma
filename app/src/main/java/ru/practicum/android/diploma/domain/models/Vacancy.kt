package ru.practicum.android.diploma.domain.models

import java.io.Serializable

data class Vacancy(
    val id: String, // Идентификатор вакансии
    val titleOfVacancy: String, // Название вакансии
    val regionName: String?, // Название региона, в котором размещена вакансия
    val salary: String, // Зарплата
    val employerName: String, // Название компании-работодателя
    val employerLogoUrl: String?, // Ссылка на логотип компании-работодателя
    val experience: String?, // Опыт работы
    val employmentType: String?, // Тип занятости (удаленка или нет)
    val scheduleType: String?, // Расписание (полный рабочий день и т. п.)
    val keySkills: String?, // Ключевые навыки
    val description: String?, // Описание вакансии, в которое входят обязанности, требования, условия
    val alternateUrl: String? // Ссылка на вакансию для шаринга
) : Serializable
