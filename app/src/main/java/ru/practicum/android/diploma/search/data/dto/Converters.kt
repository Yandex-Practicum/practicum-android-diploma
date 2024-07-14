package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.domain.models.Vacancy

fun VacancyDto.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        company = this.employer.name,
        currency = this.salary?.currency ?: "",
        salaryFrom = (this.salary?.from ?: "").toString(),
        salaryTo = (this.salary?.to ?: "").toString(),
        area = this.area.name,
        icon = this.employer.logoUrls?.logo240 ?: ""
    )
}
