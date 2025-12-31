package ru.practicum.android.diploma.favorites.vacancies.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    // Название и описание вакансии
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,

    // Зарплата
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,

    // Адрес организации
    val city: String?,
    val street: String?,
    val building: String?,
    val fullAddress: String?,

    // Требуемый опыт
    val experienceId: String?,
    val experienceName: String?,

    // Тип занятости
    val employmentId: String?,
    val employmentName: String?,

    // График работы
    val scheduleId: String?,
    val scheduleName: String?,

    // Работодатель
    val employerId: String,
    val employerName: String,
    val employerLogoUrl: String?,

    // Регион и индустрия
    val areaId: Int?,
    val areaName: String?,
    val industryId: Int?,
    val industryName: String?,

    // Ключевые навыки
    val skills: List<String>,

    // Контакты
    val contactId: String?,
    val contactName: String?,
    val contactEmail: String?,
    val contactPhones: List<String>,

    // Прочее
    val url: String,

    // Время добавления в избранное (для сортировки)
    val addedAt: Long
)
