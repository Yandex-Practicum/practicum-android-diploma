package ru.practicum.android.diploma.domain.models

import java.util.Objects

abstract class AbstractVacancy {
    abstract val id: String
    abstract val name: String
    abstract val areaName: String
    abstract val employerName: String
    abstract val employerUrls: String?
    abstract val salaryFrom: Int?
    abstract val salaryTo: Int?
    abstract val salaryCurr: String

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
