package ru.practicum.android.diploma.domain.vacancy.models

import ru.practicum.android.diploma.domain.models.AbstractVacancy

data class Vacancy(
    override val id: String,
    override val name: String,
    override val areaName: String,
    override val employerName: String,
    override val employerUrls: String?,
    override val salaryFrom: Int?,
    override val salaryTo: Int?,
    override val salaryCurr: String
) : AbstractVacancy() {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Vacancy) {
            return false
        }
        with(other) {
            return other.name == this.name &&
                other.areaName == this.areaName &&
                other.employerName == this.employerName &&
                other.salaryFrom == this.salaryFrom &&
                other.salaryTo == this.salaryTo
        }
    }
}
