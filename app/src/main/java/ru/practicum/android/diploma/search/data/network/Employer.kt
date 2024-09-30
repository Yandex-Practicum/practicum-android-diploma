package ru.practicum.android.diploma.search.data.network

data class Employer(
    val alternateUrl: String,
    val blacklisted: Boolean,
    val id: String,
    val logoUrl: String?,
    val name: String,
    val trusted: Boolean,
    val url: String,
)
