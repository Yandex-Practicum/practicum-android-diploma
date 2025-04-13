package ru.practicum.android.diploma.data.dto

data class Industry(
    var id: String,
    var name: String,
    var subIndustries: ArrayList<SubIndustry> = arrayListOf()
)
