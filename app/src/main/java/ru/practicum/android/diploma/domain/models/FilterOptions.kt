package ru.practicum.android.diploma.domain.models

data class FilterOptions(
    val searchText: String,
    val area: String,
    val industry: String,
    val currency: String,
    val salary: Int?,
    val onlyWithSalary: Boolean,
    val page: Int
)
