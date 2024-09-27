package ru.practicum.android.diploma.data.networkclient.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("accept_incomplete_resumes") val acceptIncompleteResumes: Boolean,
    val address: ru.practicum.android.diploma.data.networkclient.data.dto.Address,
    @SerializedName("alternate_url") val alternateUrl: String,
    @SerializedName("apply_alternate_url") val applyAlternateUrl: String,
    val area: ru.practicum.android.diploma.data.networkclient.data.dto.Area,
    @SerializedName("brand_snippet") val brandSnippet: ru.practicum.android.diploma.data.networkclient.data.dto.BrandSnippet,
    val branding: ru.practicum.android.diploma.data.networkclient.data.dto.Branding,
    val contacts: ru.practicum.android.diploma.data.networkclient.data.dto.Contacts,
    val counters: ru.practicum.android.diploma.data.networkclient.data.dto.Counters,
    val department: ru.practicum.android.diploma.data.networkclient.data.dto.Department,
    val employer: ru.practicum.android.diploma.data.networkclient.data.dto.Employer,
    @SerializedName("has_test") val hasTest: Boolean,
    val id: String,
    @SerializedName("insider_interview") val insiderInterview: ru.practicum.android.diploma.data.networkclient.data.dto.InsiderInterview,
    val name: String,
    @SerializedName("personal_data_resale") val personalDataResale: Boolean,
    @SerializedName("professional_roles") val professionalRoles: List<ru.practicum.android.diploma.data.networkclient.data.dto.ProfessionalRole>,
    @SerializedName("published_at") val publishedAt: String,
    val relations: List<String>,
    @SerializedName("response_letter_required") val responseLetterRequired: Boolean,
    @SerializedName("response_url") val responseUrl: String?,
    val salary: ru.practicum.android.diploma.data.networkclient.data.dto.Salary,
    val schedule: ru.practicum.android.diploma.data.networkclient.data.dto.Schedule,
    @SerializedName("show_logo_in_search") val showLogoInSearch: Boolean,
    val snippet: ru.practicum.android.diploma.data.networkclient.data.dto.Snippet,
    @SerializedName("sort_point_distance") val sortPointDistance: Double,
    val type: ru.practicum.android.diploma.data.networkclient.data.dto.Type,
    val url: String,
) : Parcelable
