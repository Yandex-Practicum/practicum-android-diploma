package ru.practicum.android.diploma.domain.models

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
    val experience: String,
    val professionalRoles: List<String>,
    val description: String,
    val schedule: List<String>,
    val address: String,
    var isFavorite: Boolean
) : AbstractVacancy()
