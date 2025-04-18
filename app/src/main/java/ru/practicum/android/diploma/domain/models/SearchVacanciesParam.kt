package ru.practicum.android.diploma.domain.models

data class SearchVacanciesParam(
    val areaIDs: MutableList<String>? = null,
    val industryIDs: MutableList<String>? = null,
    val salary: UInt? = null,
    val onlyWithSalary: Boolean? = null,
    val page: Int = 0,
    val perPage: Int = 20
)
