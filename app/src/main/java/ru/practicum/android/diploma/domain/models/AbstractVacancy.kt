package ru.practicum.android.diploma.domain.models

abstract class AbstractVacancy {
    abstract val id: String
    abstract val name: String
    abstract val areaName: String
    abstract val employerName: String
    abstract val employerUrls: String?
    abstract val salaryFrom: Int?
    abstract val salaryTo: Int?
    abstract val salaryCurr: String
}
