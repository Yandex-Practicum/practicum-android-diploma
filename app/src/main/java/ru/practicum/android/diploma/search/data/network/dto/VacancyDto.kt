package ru.practicum.android.diploma.search.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class VacancyDto(
    val accept_incomplete_resumes: Boolean? = false,
    val address: Address? = Address(),
    val alternate_url: String? = "",
    val apply_alternate_url: String? = "",
    val area: Area?,
    val branding: Branding? = Branding(),
    val contacts: Contacts? = Contacts(),
    val counters: Counters? = Counters(),
    val department: Department? = Department(),
    val employer: Employer? = Employer(),
    val has_test: Boolean? = false,
    val id: String? = "",
    val insider_interview: InsiderInterview? = InsiderInterview(),
    val name: String? = "",
    val professional_roles: List<ProfessionalRole?>? = emptyList(),
    val published_at: String? = "",
    val response_letter_required: Boolean? = false,
    val salary: Salary? = Salary(),
    val schedule: Schedule? = Schedule(),
    val show_logo_in_search: Boolean? = false,
    val snippet: Snippet? = Snippet(),
    val sort_point_distance: Double?,
    val type: Type? = Type(),
    val url: String? = "",
)