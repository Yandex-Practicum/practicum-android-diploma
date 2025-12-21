package ru.practicum.android.diploma.search.data.mapper

class FilterMapper {
    fun buildVacancyQueryMap(
        area: Int? = null,
        industry: Int? = null,
        text: String? = null,
        salary: Int? = null,
        page: Int? = null,
        onlyWithSalary: Boolean? = null
    ): Map<String, String> {

        val map = mutableMapOf<String, String>()

        area?.let { map["area"] = it.toString() }
        industry?.let { map["industry"] = it.toString() }
        text?.let { map["text"] = it }
        salary?.let { map["salary"] = it.toString() }
        page?.let { map["page"] = it.toString() }
        onlyWithSalary?.let { map["only_with_salary"] = it.toString() }

        return map
    }
}
