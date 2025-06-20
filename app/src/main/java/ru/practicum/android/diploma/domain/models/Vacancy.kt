package ru.practicum.android.diploma.domain.models

import java.util.Objects

data class Vacancy(
    val id: String,
    val name: String,
    val areaName: String,
    val employerName: String,
    val employerUrls: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurr: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vacancy) {
            return false
        } else {
            if (other.id == this.id && other.name == this.name) return true
        }
        return false
    }

    override fun hashCode() = Objects.hash(id, name)
}
