package ru.practicum.android.diploma.search.data.dto.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VacancyDto(
    val accept_incomplete_resumes: Boolean,
    val alternate_url: String,
    val apply_alternate_url: String,
    val area: Area,
    val department: Department?,
    val employer: Employer,
    val has_test: Boolean,
    val id: String,
    val name: String,
    val professional_roles: List<ProfessionalRoles>,
    val published_at: String,
    val relations: Array<String>?,
    val response_letter_required: Boolean,
    val salary: Salary?,
    val type: Type,
    val url: String,
    val snippet: Snippet,
    val found: Int,
    val page: Int,
    val pages: Int,
    val per_page: Int,
) : Parcelable

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