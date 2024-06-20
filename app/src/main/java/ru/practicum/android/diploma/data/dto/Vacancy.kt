package ru.practicum.android.diploma.data.dto

data class Vacancy(
    val id: String,
    val name: String,
    val employer: Employer,
    val salary: Salary?,
    val area: Area,
    val publishedAt: String,
    val url: String?
)

data class Employer(
    val logo_urls: LogoUrls?,
    val name: String
)

data class Salary(
    val currency: String?,
    val from: Int?,
    val to: Int?
)

data class Area(
    val id: String,
    val name: String
)

data class LogoUrls(
    val original: String?
)
