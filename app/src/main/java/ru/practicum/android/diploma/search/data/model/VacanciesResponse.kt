package ru.practicum.android.diploma.search.data.model

import com.google.gson.annotations.SerializedName

data class VacanciesResponse(
    val found: Int,
    val items: List<Vacancy>
) : Response()

data class Vacancy(
    val id: Int,
    val name: String,
    val salary: Salary?,
    val employer: Employer
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val to: Int?,
)

data class Employer(
    val name: String,
    @SerializedName("logo_urls")
    val logoUrls: Map<String, String>?
) {
    val logoUrl: String?
        get() = logoUrls?.get("240") ?: logoUrls?.get("original") ?: logoUrls?.get("90")
}

