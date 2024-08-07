package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.components.Area
import ru.practicum.android.diploma.data.components.Employer
import ru.practicum.android.diploma.data.components.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

data class VacancyDto(
    val id: Int,
    val name: String,
    val employer: Employer,
    val salary: Salary?,
    val area: Area
) {
    fun toVacancy(): Vacancy {
        return Vacancy(
            id = id,
            name = name,
            company = employer.name,
            currency = salary?.currency.orEmpty(),
            salaryFrom = salary?.from,
            salaryTo = salary?.to,
            area = area.name,
            icon = employer.logoUrls?.logo240 ?: ""
        )
    }
}
