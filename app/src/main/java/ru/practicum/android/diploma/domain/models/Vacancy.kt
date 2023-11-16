package ru.practicum.android.diploma.domain.models


data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employerName: String?,
    val found: Int,
    val employerLogoUrl: String?,
    val salary: Salary?
): RowType()