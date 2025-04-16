package ru.practicum.android.diploma.domain.models

data class Industry(
    val id: String,
    val name: String,
    val subIndustries: List<SubIndustry> = arrayListOf()
)
