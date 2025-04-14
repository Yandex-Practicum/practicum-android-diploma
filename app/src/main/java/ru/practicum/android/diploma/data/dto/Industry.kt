package ru.practicum.android.diploma.data.dto

data class Industry(
    val id: String,
    val name: String,
    val subIndustries: ArrayList<SubIndustry> = arrayListOf()
)
