package ru.practicum.android.diploma.network_client.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    val accept_incomplete_resumes: Boolean,
    val address: Address,
    val alternate_url: String,
    val apply_alternate_url: String,
    val area: Area,
    val brand_snippet: BrandSnippet,
    val branding: Branding,
    val contacts: Contacts,
    val counters: Counters,
    val department: Department,
    val employer: Employer,
    val has_test: Boolean,
    val id: String,
    val insider_interview: InsiderInterview,
    val name: String,
    val personal_data_resale: Boolean,
    val professional_roles: List<ProfessionalRole>,
    val published_at: String,
    val relations: List<String>,
    val response_letter_required: Boolean,
    val response_url: String?,
    val salary: Salary,
    val schedule: Schedule,
    val show_logo_in_search: Boolean,
    val snippet: Snippet,
    val sort_point_distance: Double,
    val type: Type,
    val url: String
) : Parcelable
