package ru.practicum.android.diploma.domain.models

import java.util.Objects

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
) : AbstractVacancy() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is VacancyDetail) {
            return false
        } else {
            if(other.id == this.id && other.name == this.name) return true
        }
        return false
    }

    override fun hashCode() = Objects.hash(id, name)
}
