package ru.practicum.android.diploma.domain.models.main

data class Vacancy(
    val id: String,
    val employerImgUrl: String,
    val employer: String,
    val name: String,
    val salary: String
)
