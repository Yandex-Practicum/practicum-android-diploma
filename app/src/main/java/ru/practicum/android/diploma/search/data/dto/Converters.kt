package ru.practicum.android.diploma.search.data.dto

import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.utils.formattingSalary

fun VacancyDto.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        name = this.name,
        company = this.employer.name,
        salary = formattingSalary(this.salary),
        area = this.area.name,
        icon = this.employer.logoUrls?.logo240 ?: ""
    )
}
