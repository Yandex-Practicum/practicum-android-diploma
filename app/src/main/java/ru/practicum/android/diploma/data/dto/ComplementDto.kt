package ru.practicum.android.diploma.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Area(
    val id: String,
    val name: String,
    val url: String,
) : Parcelable

@Parcelize
data class Department(
    val id: String,
    val name: String,
) : Parcelable

@Parcelize
data class Employer(
    val accredited_it_employer: Boolean,
    val alternate_url: String?,
    val id: String?,
    val logo_urls: LogoUrls?,
    val name: String,
    val trusted: Boolean,
    val url: String?,
    val vacancies_url: String?,
) : Parcelable

@Parcelize
data class ProfessionalRoles(
    val id: String,
    val name: String,
) : Parcelable

@Parcelize
data class Salary(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?,
) : Parcelable

@Parcelize
data class Type(
    val id: String,
    val name: String,
) : Parcelable

@Parcelize
data class Snippet(
    val requirement: String?,
    val responsibility: String?,
) : Parcelable

@Parcelize
data class LogoUrls(
    val v90: String,
    val v240: String,
    val original: String,
) : Parcelable


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
