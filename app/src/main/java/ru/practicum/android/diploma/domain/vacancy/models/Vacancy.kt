package ru.practicum.android.diploma.domain.vacancy.models

data class Vacancy(
    val id: String,
    val title: String,
    val employerTitle: String,
    val logoUrl: String?,
    val salaryRange: VacancySalaryRange?,
) {
    data class VacancySalaryRange(
        val currency: String,
        val from: Int?,
        val to: Int?,
    )
}
