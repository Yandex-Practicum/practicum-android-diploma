package ru.practicum.android.diploma.ui.filter.model

data class SelectedFilters(
    val placeId: String?,
    val place: String?,
    val industryId: String?,
    val industry: String?,
    val salary: Int?,
    val onlyWithSalary: Boolean
)
