package ru.practicum.android.diploma.features.vacancydetails.domain.models

data class Salary(
    val salaryCurrency: String?,
    val salaryLowerBoundary: Int?,
    val salaryUpperBoundary: Int?
)