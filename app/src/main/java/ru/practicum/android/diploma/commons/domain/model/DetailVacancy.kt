package ru.practicum.android.diploma.commons.domain.model

data class DetailVacancy(
    val id: String,
    val areaName: String?,
    val areaUrl: String?,
    val contactsEmail: String?,
    val contactsName: String?,
    val contactsPhones: List<String>?,
    val comment: String?,
    val description: String?,
    val employerName: String?,
    val employmentName: String?,
    val experienceName: String?,
    val keySkillsNames: List<String?>,
    val name: String?,
    val salaryCurrency: String?,
    val salaryFrom: Int?,
    val salaryGross: Boolean?,
    val salaryTo: Int?,
    val scheduleId: String?,
    val scheduleName: String?
)
