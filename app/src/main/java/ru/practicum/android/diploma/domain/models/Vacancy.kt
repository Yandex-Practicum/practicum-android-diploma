package ru.practicum.android.diploma.domain.models

import android.os.Parcelable

data class Vacancy(
    val id: String,
    val name: String,
    val vacancyTitle: String,
    val description: String,
    val experience: String?,
    val schedule: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val currency: String?,
    val salaryTitle: String,
    // Поля адреса
    val city: String?,
    val street: String?,
    val email: String?,
    val phone: List<String>?,
    // Работодатель
    val employerName: String,
    val logoUrl: String?
) : Parcelable
