package ru.practicum.android.diploma.domain.models

data class VacancySearchRequest(
    val text: String,
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val page: Int = FIRST_PAGE,
    val onlyWithSalary: Boolean? = null,
) {
    companion object {
        const val FIRST_PAGE = 1
    }
}
