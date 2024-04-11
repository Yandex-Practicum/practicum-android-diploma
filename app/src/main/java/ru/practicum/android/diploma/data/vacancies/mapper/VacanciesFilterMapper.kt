package ru.practicum.android.diploma.data.vacancies.mapper

import ru.practicum.android.diploma.domain.models.Filters

object VacanciesFilterMapper {
    fun map(query: String, page: Int, filters: Filters): Map<String, String> {
        val result = mutableMapOf<String, String>()
        result["text"] = query
        result["page"] = page.toString()
        if (filters.area != null) {
            result["area"] = filters.area
        }
        if (filters.industry != null) {
            result["industry"] = filters.industry
        }
        if (filters.salary != null) {
            result["salary"] = filters.salary
        }
        if (filters.salaryBoolean != null) {
            result["only_with_salary"] = filters.salaryBoolean
        }
        return result
    }


}
