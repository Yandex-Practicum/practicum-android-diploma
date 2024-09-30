package ru.practicum.android.diploma.data.networkclient.api.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("accept_incomplete_resumes") val acceptIncompleteResumes: Boolean,
    val address: Address,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("apply_alternate_url") val applyAlternateUrl: String,
    val area: Area,
    @SerializedName("brand_snippet") val brandSnippet: BrandSnippet,
    val branding: Branding,
    val contacts: Contacts,
    val counters: Counters,
    val department: Department,
    val employer: Employer,
    @SerializedName("has_test") val hasTest: Boolean,
    val id: String,
    @SerializedName("insider_interview") val insiderInterview: InsiderInterview,
    val name: String,
    @SerializedName("personal_data_resale") val personalDataResale: Boolean,
    @SerializedName("professional_roles") val professionalRoles: List<ProfessionalRole>,
    @SerializedName("published_at") val publishedAt: String,
    val relations: List<String>,
    @SerializedName("response_letter_required") val responseLetterRequired: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
    val salary: Salary?,
    val schedule: Schedule,
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean,
    val snippet: Snippet,
    @SerializedName("sort_point_distance") val sortPointDistance: Double,
    val type: Type,
    val url: String,
) : Parcelable
