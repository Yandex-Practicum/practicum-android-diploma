package ru.practicum.android.diploma.data.dto


import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
