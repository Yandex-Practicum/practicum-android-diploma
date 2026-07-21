package ru.practicum.android.diploma.data.network

data class VacanciesRequest(
    val searchText: String,
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val page: Int = 0,
    val onlyWithSalary: Boolean = false
)

fun VacanciesRequest.toMap(): Map<String, String> {
    val options = mutableMapOf<String, String>()
    options["text"] = searchText
    options["page"] = page.toString()
    area?.let { options["area"] = it.toString() }
    industry?.let { options["industry"] = it.toString() }
    salary?.let { options["salary"] = it.toString() }
    if (onlyWithSalary) {
        options["only_with_salary"] = "true"
    }
    return options
}
