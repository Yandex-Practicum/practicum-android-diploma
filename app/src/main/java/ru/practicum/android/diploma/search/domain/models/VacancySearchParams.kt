package ru.practicum.android.diploma.search.domain.models

data class VacancySearchParams(
    val query: String = "",
    val perPage: Int = 20,
    val page: Int = 0,
)

fun VacancySearchParams.toMap(): Map<String, String> {
    return mapOf(
        "text" to query,
        "per_page" to perPage.toString(),
        "page" to page.toString()
    )
}
