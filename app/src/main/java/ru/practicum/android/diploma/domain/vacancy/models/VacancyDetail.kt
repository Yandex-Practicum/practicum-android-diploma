package ru.practicum.android.diploma.domain.vacancy.models

import ru.practicum.android.diploma.domain.models.AbstractVacancy

data class VacancyDetail(
    override val id: String,
    override val name: String,
    override val areaName: String,
    override val employerName: String,
    override val employerUrls: String?,
    override val salaryFrom: Int?,
    override val salaryTo: Int?,
    override val salaryCurr: String,
    val keySkills: List<String>,
    val employmentForm: String,
    val professionalRoles: List<String>,
    val experience: String,
    val description: String,
) : AbstractVacancy()
