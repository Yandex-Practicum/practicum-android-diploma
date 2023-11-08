package ru.practicum.android.diploma.data.dto.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.Salary

@Parcelize
data class FullVacancyDto(
    val id: String,
    val name: String,
    val area: Area,
    val brandedDescription: String?,
    val contacts: Contacts?,
    val description: String?,
    val employer: Employer?,
    val experience: Experience?,
    val employment: EmploymentType?,
    val salary: Salary?,
    val keySkills: List<SkillName>?,
    val driverLicense: List<License>?,
    val languages: List<Language>?,
) : Parcelable, Response()

@Parcelize
data class EmploymentType(
    val name: String?
) : Parcelable

@Parcelize
data class SkillName(
    val name: String?
) : Parcelable


@Parcelize
data class License(
    val id: String?
) : Parcelable


@Parcelize

data class Language(
    val name: String?,
    val level: Level,
) : Parcelable


@Parcelize
data class Level(
    val id: String?,
    val name: String?
) : Parcelable


@Parcelize
data class Contacts(
    val email: String?,
    val name: String?,
    val phones: List<Phone>,
) : Parcelable


@Parcelize
data class Phone(
    val city: String?,
    val comment: String?,
    val country: String?,
    val number: String?
) : Parcelable


@Parcelize
data class Experience(
    val id: String?,
    val name: String?,
) : Parcelable
